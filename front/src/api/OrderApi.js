import http from "@/utils/http";

// 用户
export function getOrder(orderId) {
    return http.get(
        `api/order/${orderId}`
    )
}

export function getUserOrder(pageNo, pageSize, userId) {
    return http.get(
        `api/user_order/${pageNo}/${pageSize}/${userId}`
    )
}

export function shopkeeperOrder(pageNo, pageSize, shopkeeperId) {
    return http.get(
        `api/shopkeeper_order/${pageNo}/${pageSize}/${shopkeeperId}`
    )
}

// 获取管理员权限的所有购物信息
export function getAdminOrder(pageNo, pageSize) {
    return http.get(
        `api/admin_order/${pageNo}/${pageSize}`
    )
}

// 添加购物车
export function addOrder(data) {
    return http.post(
        'api/order',
        data
    )
}
// 删除order
export function deleteOrder(data) {
    return http.delete(
        'api/order',
        data
    )
}

// 购买
export function buy(data) {
    return http.put(
        'api/order',
        data
    )
}
// 确认收货
export function confirmOrder(data) {
    return http.put(
        'api/confirm_order',
        data
    )
}
// 退款
export function backOrder(data) {
    return http.put(
        'api/back_order',
        data
    )
}
// 评论
export function commentOrder(data) {
    return http.put(
        'api/comment_order',
        data
    )
}
// 企业修改,管理员比企业多了一个回扣可以修改
export function alterOrder(data) {
    return http.put(
        'api/alter_order',
        data
    )
}

