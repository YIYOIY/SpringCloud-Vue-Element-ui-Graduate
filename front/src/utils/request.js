import axios from "axios";

//创建axios实例
const httpService = axios.create({
    // baseURL: "https://localhost:89/api",
    timeout: 5000,//超时时间
})

//请求拦截
httpService.interceptors.request.use((config) => {//请求的数据
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let token = sessionStorage.getItem("token") ? sessionStorage.getItem("token") : null
    if (token) {
        config.headers['Authorization'] ="Bearer "+token // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config//必须返回出去，不然请求发不出去
}, error => {
    console.log(error+"错误请求！")
    //处理错误请求
    return Promise.reject(error)
})
//响应拦截：后端返回来的结果
httpService.interceptors.response.use((res) => {
    const code = res.data.code//code是后端的状态码
    if (code !== 200) {
        return Promise.reject(res)
    } else {
        //请求成功
        console.log(res, '成功----')
        // 在这里已经削了一层包装了，所以加下来直接可以用数据
        return Promise.resolve(res.data)
    }
},error=> {
    console.log(error+'错误信息的处理')//错误信息的处理
    switch (error.data.code) {
        case 400:
            error.message = '错误请求'
            break;
        case 401:
            error.message = '未授权，请重新登录'
            break;
        case 403:
            error.message = '拒绝访问'
            break;
        case 404:
            error.message = '请求错误,未找到该资源'
            window.location.href = "/NotFound"
            break;
        case 405:
            error.message = '请求方法未允许'
            break;
        case 408:
            error.message = '请求超时'
            break;
        case 500:
            error.message = '服务器端出错'
            break;
        case 501:
            error.message = '网络未实现'
            break;
        case 502:
            error.message = '网络错误'
            break;
        case 503:
            error.message = '服务不可用'
            break;
        case 504:
            error.message = '网络超时'
            break;
        case 505:
            error.message = 'http版本不支持该请求'
            break;
        default:
            error.message = `连接错误${error.response.status}`
    }
    //处理错误响应
    return Promise.reject(error)
})
//因为别的地方要用，所以就把实例暴露出去，导出
export default httpService