import http from "@/utils/http";

export function getPicture() {
    return http.get(
        'pict/getPicture'
    )
}
