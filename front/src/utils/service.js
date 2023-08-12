// axios封装
import axios from "axios";
// 控制是否使用token和baseUrl设置，在另一个文件里
import serverConfig from "@/utils/serviceConfig";
import JSONBIG from 'json-bigint'

// 解决id使用uuid精度丢失问题
axios.defaults.transformResponse = [
    function (data) {
        const json = JSONBIG({
            storeAsString: true
        })
        return json.parse(data)
    }
]


//创建axios实例
const serviceAxios = axios.create({
    baseURL: serverConfig.baseURL, // 基础请求地址
    timeout: 5000,//超时时间
    withCredentials: false, // 跨域请求是否需要携带 cookie
})
// 创建请求拦截
serviceAxios.interceptors.request.use(
    (config) => {
        // 如果开启 token 认证
        if (serverConfig.useTokenAuthorization) {
            let token = sessionStorage.getItem("token") ? sessionStorage.getItem("token") : null
            config.headers['Authorization'] = "Bearer " + token // 让每个请求携带自定义token 请根据实际情况自行修改
        }
        // 设置请求头
        if (!config.headers["content-type"]) { // 如果没有设置请求头
            config.headers["content-type"] = "application/json;charset=utf-8"; // 默认类型
            // if (config.method === 'post') {
            //     config.headers["content-type"] = "application/x-www-form-urlencoded"; // post 请求
            //     config.data = qs.stringify(config.data); // 序列化,比如表单数据
            // } else {
            //     config.headers["content-type"] = "application/json"; // 默认类型
            // }
        }
        // Content-Type用于指定消息体格式，get请求没有消息体，所以不用设置，设置了也是多此一举
        // 1，application/json，现在越来越多的人把它作为请求头，用来告诉服务端消息主体是序列化后的 JSON 字符串。,使用contentType: “application/json”则data只能是json字符串
        // 2, application/x-www-form-urlencoded，form表单默认的数据格式，提交的数据按照 key1=val1&key2=val2 的方式进行编码，key 和 val 都进行了 URL 转码。（ajax默认的Content-Type类型,application/x-www-form-urlencoded是默认的请求头，其ajax的请求数据格式是json
        // 3，multipart/form-data，对用于在表单中上传文件时，也可以上传普通数据，只需要让from的ectyle等于multipart/form-data就可以了。
        // console.log("请求配置", config);
        return config;
    }, (error) => {
        console.log(error + "错误请求！")
        //处理错误请求
        Promise.reject(error)
    })

// 创建响应拦截
serviceAxios.interceptors.response.use((res) => {
    // 处理自己的业务逻辑，比如判断 token 是否过期等等
    // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
    // 否则的话抛出错误
    if (res.data.code === 200) {//code是后端的状态码
        if (typeof res.data.data != 'undefined') {
            //请求成功
            // console.log(res, '成功----')
            // 在这里已经削了一层包装了，所以加下来直接可以用数据
            return Promise.resolve(res.data);
        } else {
            return Promise.reject(res);
        }
    } else {
        return Promise.reject(res);
    }
}, error => {
    console.log(error + '错误信息的处理')//错误信息的处理
    let message = "";
    if (error && error.response) {
        switch (error.response.status) {
            case 302:
                message = "接口重定向了！";
                break;
            case 400:
                message = "参数不正确！";
                break;
            case 401:
                message = "您未登录，或者登录已经超时，请先登录！";
                break;
            case 403:
                message = "您没有权限操作！";
                break;
            case 404:
                message = `请求地址出错: ${error.response.config.url}`;
                break;
            case 408:
                message = "请求超时！";
                break;
            case 409:
                message = "系统已存在相同数据！";
                break;
            case 500:
                message = "服务器内部错误！";
                break;
            case 501:
                message = "服务未实现！";
                break;
            case 502:
                message = "网关错误！";
                break;
            case 503:
                message = "服务不可用！";
                break;
            case 504:
                message = "服务暂时无法访问，请稍后再试！";
                break;
            case 505:
                message = "HTTP 版本不受支持！";
                break;
            default:
                message = "异常问题，请联系管理员！";
                break;
        }
    }
    if (message !== "" && message !== null) {
        error.data.message = message
    }
    return Promise.reject(error)
})

//因为别的地方要用，所以就把实例暴露出去，导出
export default serviceAxios;