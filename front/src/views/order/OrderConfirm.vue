<template>
    <div>
        <div style="width:35%;margin: 3% 5%;float:left">
            <el-row>
                <el-form label-width="100" label-position="left" :model="customer">
                    <el-row :gutter="4">
                        <el-col :span="10">
                            <el-form-item label="用户">
                                <el-input type="input" clearable v-model="customer.name" placeholder="收货人"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10">
                            <el-form-item label="电话">
                                <el-input type="input" clearable v-model="customer.phone" placeholder="联系电话"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="20">
                            <el-form-item label="收货地址">
                                <el-input type="textarea" clearable :autosize="{ minRows: 2, maxRows: 4 }"
                                    v-model="customer.address" placeholder="请输入收货地址"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-row>

            <el-row>
                <el-col :span="12" style="height:10%;width:50%;font-size:28px">
                    {{ order.book.bookName }}
                </el-col>
                <el-col :span="12" style="height:10%;width:50%;font-size:18px">
                    <span>￥{{ order.book.bookPrice }} 元</span>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="8"
                    style="padding: 0 5%;height:10%;font-size:18px;color:orange;border: solid 2px;margin:3% 2%;">
                    <el-text style="color:orange;">7天无理由退换</el-text>
                </el-col>

                <el-col :span="12" style="height:10%;width:40%;font-size:18px;margin:3% 0%;margin-left:12%">
                    X {{ order.bookNum }}
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="24" style="height:15%;font-size:28px;margin:0% 6%;margin-bottom:5%">
                    <el-text type="text" style="height:15%;font-size:18px">预计今日24:00前发货</el-text>
                </el-col>
            </el-row>

            <el-row style="height:10%;width:100%;font-size:18px;margin:1% 0%">
                <el-col :span="12">
                    总价
                </el-col>
                <el-col :span="12">
                    {{ (order.book.bookPrice) * order.bookNum }} 元
                </el-col>
            </el-row>

            <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0%">
                <el-col :span="12">
                    运费
                </el-col>
                <el-col :span="12">
                    <span>{{ customer.customer.expressFare }} 元</span>
                </el-col>

            </el-row>

            <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0%">
                <el-col :span="12">
                    订单状态
                </el-col>
                <el-col :span="12">
                    {{ order.orderStatus }}
                </el-col>

            </el-row>

            <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0%">
                <el-col :span="12">
                    订单编号
                </el-col>
                <el-col :span="12">
                    {{ order.orderId }}
                </el-col>
            </el-row>

            <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0%">
                <el-col :span="12">
                    添加购物车时间
                </el-col>
                <el-col :span="12">
                    <el-date-picker size="small" v-model="order.orderTime" format="YYYY年MM月DD日HH时mm分ss秒" placeholder="购买日期"
                        disabled>
                    </el-date-picker>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-button class="el-button" round color="#626aef"
                        @click="buy(order.orderId, order.bookNum, order.bookId)"
                        :disabled="(order.orderStatus == `已购买`) || (order.bookNum > order.book.bookNum)">购买</el-button>
                </el-col>
                <el-col :span="12">
                    <el-button class="el-button" round type="danger" @click="back">返回</el-button>
                </el-col>
            </el-row>

        </div>
        <div style="width:35%;margin: 2% 2%;float:right">
            <el-image :src="order.book.bookPicture" style="width:70%"></el-image>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref, toRef } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios'
import { useStore } from "vuex";
import { ElMessage, ElNotification } from "element-plus";
let router = useRouter()
let store = useStore();
const prop = defineProps(['orderId'])
let orderId = toRef(prop, 'orderId')
let order = ref([])

let customer = reactive({
    customer: {
        name: "",
        phone: "",
        address: "",
        orderId: "",
        bookNum: "",
        bookPrice: "",
        totalPrice: "",
        addTime: "",
        PayTime: "",
        expressFare: "0.00"
    }
})

console.log(orderId.value)
axios.get(`api/order?orderId=${orderId.value}`).then(Response => {
    order.value = Response.data
    console.log(order.value)
})



let buy = ((v, num, bookId) => {
    console.log("这里是购物车的购买" + v)
    if (confirm("确认购买?")) {
        axios.put(`api/buyOrder?orderId=${v}&num=${num}&bookId=${bookId}`).then(Response => {
            let message = Response.data
            ElNotification.success({
                message: message,
                position: 'top-left',
                title: '购买成功'
            })
            axios.get(`api/userOrder?pageNo=${pageNo.value}&userId=${store.state.userId}`).then(Response => {
                order.value = Response.data
            })
        }).catch(error => {
            ElMessage.error(error.response.data)
        })
    }
})

let back = ((v) => {
    router.push({
        name: 'userOrder'
    })
})

</script>
<style scoped>
/deep/ .bookName .cell {
    padding-left: 25%;
}

.el-button {
    margin-left: 10%;
    width: 80%;
    margin-top: 2%;
}
</style>
