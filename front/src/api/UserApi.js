import http from "@/utils/http";

export function addUser(data) {
    return http.post(
        'api/add_user',
        data
    )
}

export function deleteUser(params) {
    return http.delete(
        `api/user?userId=${params}`
    )
}

export function getUsers() {
    return http.get(
        'api/users'
    )
}

export function alterUserGet(params) {
    return http.get(
        `api/user?userId=${params}`
    )
}

export function alterUser(params) {
    return http.put(
        "api/user",
        params
    )
}