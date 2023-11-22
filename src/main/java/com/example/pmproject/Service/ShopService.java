package com.example.pmproject.Service;
import com.example.pmproject.Entity.Shop;
import java.util.ArrayList;
import java.util.List;


public class ShopService {
    private List<Shop> shops;

    public ShopService() {
        this.shops = new ArrayList<>();
    }

    public void addShop(int shopId, String name, String location, boolean isAdmin) {
        if (isAdmin) {
        Shop newShop = new Shop();
        newShop.setShopId((long) shopId);
        newShop.setName(name);
        newShop.setLocation(location);

        shops.add(newShop);
        System.out.println("매장 '" + name + "' 이(가) 성공적으로 추가되었습니다.");
        } else {
            System.out.println("권한이 없어 추가할 수 없습니다.");
        }
    }


    public Shop getShopById(int shopId) {
        for (Shop shop : shops) {
            if (shop.getShopId() == shopId) {
                return shop;
            }
        }
        System.out.println("매장 '" + shopId + "' 을(를) 찾을 수 없습니다.");
        return null;
    }

    public Shop getShopByIdOrLocation(String idOrLocation) {
        for (Shop shop : shops) {
            // 매장 ID로 검색
            try {
                Long id = Long.parseLong(idOrLocation);
                if (shop.getShopId().equals(id)) {
                    return shop;
                }
            } catch (NumberFormatException e) {
                // idOrLocation이 숫자로 변환할 수 없는 경우 무시
            }

            // 매장 주소로 검색 (대소문자 무시)
            if (shop.getLocation().equalsIgnoreCase(idOrLocation)) {
                return shop;
            }
        }
        System.out.println("다음 매장의 이름 또는 위치로 찾을 수 없습니다: " + idOrLocation);
        return null;
    }

    public void listAllShops() {
        if (shops.isEmpty()) {
            System.out.println("상점이 존재하지 않습니다.");
            return;
        }
        System.out.println("매장 리스트:");
        for (Shop shop : shops) {
            System.out.println("ID: " + shop.getShopId() + ", 이름: " + shop.getName() + ", 주소: " + shop.getLocation());
        }
    }

    public static void main(String[] args) {
        ShopService shopService = new ShopService();

        // 상점(Shop) 추가
        shopService.addShop(1, "매장 A", "서울", true);
        shopService.addShop(2, "매장 B", "부산", false);

        // 상점 목록 출력
        shopService.listAllShops();

        // 상점 ID로 조회
        int shopIdToFind = 1;
        Shop foundShop = shopService.getShopById(shopIdToFind);
        if (foundShop != null) {
            System.out.println("ID를 통해 매장을 찾았습니다 : " + foundShop.getShopId() + ", 이름: " + foundShop.getName() + ", 주소: " + foundShop.getLocation());
        }
        // 상점 주소로 조회
        String shopLocationToFind = "서울";
        Shop foundShopByLocation = shopService.getShopByIdOrLocation(shopLocationToFind);
        if (foundShopByLocation != null) {
            System.out.println("주소를 통해 매장을 찾았습니다 " + foundShopByLocation.getShopId() + ", 이름: " + foundShopByLocation.getName() + ", 주소: " + foundShopByLocation.getLocation());
        }
    }
}
