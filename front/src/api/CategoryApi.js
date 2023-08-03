import http from "@/utils/http";

export function addSeries(data) {
    return http.post(
        'api/bookSeries',
        data
    )
}

export function deleteSeries(params) {
    return http.delete(
        `api/bookSeries?seriesId=`+params
    )
}

export function getSeriesByID(params) {
    return http.get(
        `api/bookSeriesBySeriesId?seriesId=`+params
    )
}

export function alterSeries(params) {
    return http.put(
        'api/bookSeries',
        params
    )
}
