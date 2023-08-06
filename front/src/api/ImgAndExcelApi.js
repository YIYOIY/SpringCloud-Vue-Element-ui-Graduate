import http from "@/utils/http";

export function getPicture(id) {
    return http.get(
        `picture/getPicture/${id}`
    )
}
