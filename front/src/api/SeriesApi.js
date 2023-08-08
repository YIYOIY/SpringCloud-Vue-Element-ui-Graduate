import http from "@/utils/http";

export function getAllSeries(searchName,pageNo,pageSize) {
    return http.get(
        `api/series/${searchName}/${pageNo}/${pageSize}`
    )
}
export function getSeriesByID(params) {
    return http.get(
        `api/series/${params}`
    )
}

export function addSeries(data) {
    return http.post(
        'api/series',
        data
    )
}

export function deleteSeries(data) {
    return http.delete(
        `api/series`,
        data
    )
}

export function alterSeries(data) {
    return http.put(
        'api/series',
        data
    )
}
