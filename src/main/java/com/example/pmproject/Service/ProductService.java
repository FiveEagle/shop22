package com.example.pmproject.Service;

import com.example.pmproject.Constant.Role;
import com.example.pmproject.DTO.ProductDTO;
import com.example.pmproject.Entity.Member;
import com.example.pmproject.Entity.Product;
import com.example.pmproject.Entity.ProductComment;
import com.example.pmproject.Repository.MemberRepository;
import com.example.pmproject.Repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class ProductService {

    @Value("${productImgUploadLocation}")
    private String productImgUploadLocation;
    private final imgService imgService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper=new ModelMapper();
    private Long productId;
    private int stockQuantity;
    private final MemberRepository memberRepository;
    private final BasketService basketService;

    public Page<ProductDTO> productDTOS(Pageable pageable) {
        int page=pageable.getPageNumber()-1;
        int pageLimit=6;

        Page<Product> paging=productRepository.findAll(PageRequest.of(page, pageLimit, Sort.Direction.ASC, "productId"));

        return paging.map(product -> ProductDTO.builder()
                .name(product.getName())
                .content(product.getContent())
                .price(product.getPrice())
                .img(product.getImg())
                .build());
    }

    public void register(ProductDTO productDTO, MultipartFile imgFile) throws IOException {
        String originalFileName = imgFile.getOriginalFilename();
        String newFileName = "";

        if(originalFileName != null) {
            newFileName = imgService.uploadFile(productImgUploadLocation, originalFileName, imgFile.getBytes());
        }
        productDTO.setImg(newFileName);
        Product product=modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
    }

    public ProductDTO listOne(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        return modelMapper.map(product, ProductDTO.class);
    }

    public void modify(ProductDTO productDTO, MultipartFile imgFile) throws IOException {
        Product product = productRepository.findById(productDTO.getProductId()).orElseThrow();
        String deleteFile = product.getImg();

        String originalFileName = imgFile.getOriginalFilename();
        String newFileName = "";

        if(originalFileName.length() != 0) {
            if(deleteFile.length() != 0) {
                imgService.deleteFile(productImgUploadLocation, deleteFile);
            }

            newFileName = imgService.uploadFile(productImgUploadLocation, originalFileName, imgFile.getBytes());
            productDTO.setImg(newFileName);
        }
        Product modify = modelMapper.map(productDTO, Product.class);
        modify.setProductId(product.getProductId());

        productRepository.save(modify);
    }

    public void Basket(String email, Long productId) throws NotFoundException {
        // basket로 로직 이동
        basketService.addToCart(email, productId);
    }

    //게시글 삭제
    public void Delete(String email, Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // 현재 로그인한 사용자 가져오기
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                Member currentUser = optionalMember.get();

                // 현재 사용자가 관리자 권한 소유자인지 확인
                if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getName() != null) {
                    // 사용자가 필요한 권한을 가지고 있을 때만 삭제
                    productRepository.deleteById(productId);
                } else {
                    // 권한이 없는 삭제를 처리 (예외 던지기, 로깅 등)
                    throw new Exception("이 게시물을 삭제할 권한이 없습니다");
                }
            } else {
                // 사용자를 찾을 수 없음을 처리 (예외 던지기, 로깅 등)
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
            }
        } else {
            // 게시글을 찾을 수 없음을 처리 (예외 던지기, 로깅 등)
            throw new NotFoundException("게시물을 찾을 수 없습니다");
        }
    }
}
