import http from "@/utils/http";


export function adminGetAdmins(adminId,pageNo,pageSize,searchName) {
    return http.get(
        `api/admin/${adminId}/${pageNo}/${pageSize}/${searchName}`
    )
}

export function getAdmin(params) {
    return http.get(
        `api/admin/${params}`
    )
}

export function addAdmin(data) {
    return http.post(
        '/api/add_admin',
        data
    )
}

export function deleteAdmin(data) {
    return http.delete(
        'api/admin',
        data
    )
}
export function alterAdmin(data) {
    return http.put(
        'api/admin',
        data
    )
}