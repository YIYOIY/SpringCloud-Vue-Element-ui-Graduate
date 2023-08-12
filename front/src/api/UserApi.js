import http from "@/utils/http";


export function adminGetUsers(adminId,pageNo,pageSize,searchName) {
    return http.get(
        `api/user/${adminId}/${pageNo}/${pageSize}/${searchName}`
    )
}

export function getUser(params) {
    return http.get(
        `api/user/${params}`
    )
}

export function addUser(data) {
    return http.post(
        'api/add_user',
        data
    )
}

export function deleteUser(data) {
    return http.delete(
        'api/user',
        data
    )
}

export function alterUser(data) {
    return http.put(
        "api/user",
        data
    )
}

export function UserMoney(data) {
    return http.put(
        "api/user_money",
        data
    )
}