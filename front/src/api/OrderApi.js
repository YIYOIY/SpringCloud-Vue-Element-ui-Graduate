import http from "@/utils/http";

// 添加购物车
export function addOrder(data) {
    return http.post(
        `api/order`,
        data
    )
}

// 用户
export function getOrder(params) {
    return http.get(
        `api/order?orderId=` + params
    )
}

export function getUserOrder(pageNo, userId) {
    return http.get(
        `api/userOrder?pageNo=${pageNo}&userId=${userId}`
    )
}

export function getUserPage(params) {
    return http.get(
        `api/userPageCount?userId=` + params)
}


// 删除order
export function deleteOrder(data) {
    return http.delete(
        'api/order?orderId='+data
    )
}

// 获取管理员购物车显示页数
export function getAdminPageCount() {
    return http.get(
        'api/adminPageCount'
    )
}

// 获取管理员权限的所有购物信息
export function getAdminOrder(params) {
    return http.get(
        `api/adminOrder?pageNo=` + params
    )
}

// 购买--更新
export function checkBag(orderId, num, bookId) {
    return http.put(
        `api/buyOrder?orderId=${orderId}&num=${num}&bookId=${bookId}`,
    )
}