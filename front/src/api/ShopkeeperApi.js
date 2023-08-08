import http from "@/utils/http";

export function adminGetShopkeepers(adminId,pageNo,pageSize,searchName) {
    return http.get(
        `api/shopkeeper/${adminId}/${pageNo}/${pageSize}/${searchName}`
    )
}

export function getShopkeeper(params) {
    return http.get(
        `api/shopkeeper/${params}`
    )
}

export function addShopkeeper(data) {
    return http.post(
        'api/add_shopkeeper',
        data
    )
}

export function deleteShopkeeper(data) {
    return http.delete(
        'api/shopkeeper',
        data
    )
}

export function alterShopkeeper(data) {
    return http.put(
        "api/shopkeeper",
        data
    )
}