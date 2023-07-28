import http from "@/utils/http";

export function addAdmin(data) {
    return http.post(
        '/api/addAdmin',
        data
    )
}

export function deleteAdmin(params) {
    return http.delete(
        `api/admin?adminId=${params}`
    )
}

export function getAdmin() {
    return http.get(
        'api/admin'
    )
}

export function alterAdminGet(params) {
    return http.get(
            `api/alterAdmin?adminId=${params}`
    )
}

export function alterAdmin(params) {
    return http.put(
        `api/admin`,
        params
    )
}