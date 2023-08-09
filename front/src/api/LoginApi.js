import http from "@/utils/http";

export function adminLogin(data) {
    return http.post(
        '/api/login/admin',
        data
    )
}

export function userLogin(data) {
    return http.post(
        '/api/login/user',
        data
    )
}

export function shopkeeperLogin(data) {
    return http.post(
        '/api/login/shopkeeper',
        data
    )
}
export function checkToken(params) {
    return http.get(
        '/api/login/checkToken?token='+params,
    )
}