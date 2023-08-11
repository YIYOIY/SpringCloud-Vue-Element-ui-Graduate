<template>
  <div style="width:100%;height:100%;">
    <div style="width:45%;margin:5% 8% 0 8%;float:left">
      <el-row justify="space-around" style="height:10%;width:100%;font-size:28px">
        <el-form label-width="100" :model="order.order" style="height:10%;width:100%;font-size:28px">
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="用户">
                <el-input type="input" clearable v-model="order.order.user.userName" placeholder="收货人"
                          :disabled="!(order.order.orderStatus===`未支付`)"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="电话">
                <el-input type="input" clearable v-model="order.order.user.userPhone" placeholder="联系电话"
                          :disabled="!(order.order.orderStatus===`未支付`)"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item label="收货地址">
                <el-input type="textarea" clearable :autosize="{ minRows: 2, maxRows: 4 }"
                          v-model="order.order.user.userAddress" placeholder="请输入收货地址"
                          :disabled="!(order.order.orderStatus===`未支付`)"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>

      <el-row justify="space-around" style="height:10%;width:100%;font-size:28px">
        <el-col :span="12">
          {{ order.order.book.bookName }}
        </el-col>
        <el-col :span="12">
          <span style="height:10%;width:100%;font-size:18px" v-if="store.state.isUser">RMB￥{{
              order.order.bookPrice
            }} 元</span>
          <span style="height:10%;width:100%;font-size:18px" v-if="store.state.isShopkeeper||store.state.isAdmin">
            价格： <el-input-number v-model="order.order.bookPrice" :step=1 type="number"/></span>
        </el-col>
      </el-row>

      <el-row justify="space-evenly" style="height:20%;width:100%;font-size:28px">
        <el-col :span="12">
          <el-text style="color:orange;border: solid 2px">7天无理由退换</el-text>
        </el-col>
        <el-col :span="12" style="height:10%;width:100%;font-size:14px;margin:1% 0">
          <el-text type="primary" style="font-size:14px">预计24:00时前发货，三天内送达</el-text>
        </el-col>
      </el-row>

      <el-row justify="space-evenly" style="width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">数量</el-col>
        <el-col :span="12" v-if="store.state.isUser">{{ order.order.buyNumber }} *</el-col>
        <el-col :span="12" v-if="store.state.isShopkeeper||store.state.isAdmin">
          <el-input-number v-model="order.order.buyNumber" :step=1 :max=order.order.book.bookNumber type="number"/>
        </el-col>
      </el-row>
      <el-row justify="space-evenly" style="width:100%;font-size:18px;margin:3% 0" v-if="store.state.isAdmin">
        <el-col :span="12">回扣</el-col>
        <el-col :span="12" >
          <el-input-number v-model="order.order.kickback" :step=0.01 :min=0 :max=10 type="number"/>
        </el-col>
      </el-row>
      <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">折扣</el-col>
        <el-col :span="12" v-if="store.state.isUser">
          <span>{{ order.order.discount>0?order.order.discount*10:'商家还没有打算降价' }} %</span>
        </el-col>
        <el-col :span="12" v-if="store.state.isShopkeeper||store.state.isAdmin">
          <el-input-number :step=0.01 v-model="order.order.discount" :min=0 :max=10 type="number"/>
        </el-col>
      </el-row>
      <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">
          运费
        </el-col>
        <el-col :span="12" v-if="store.state.isUser">
          <span>{{ order.order.expressFare ? order.order.expressFare : 0 }} 元</span>
        </el-col>
        <el-col :span="12" v-if="store.state.isShopkeeper||store.state.isAdmin">
          <el-input-number :step=1 v-model="order.order.expressFare" type="number"/>
        </el-col>
      </el-row>
      <el-row justify="space-evenly" style="width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">总价</el-col>
        <el-col :span="12" style="color:#d55e5e;">{{
            (order.order.bookPrice * order.order.buyNumber) * (order.order.discount ? order.order.discount : 10) / 10
            + order.order.expressFare
          }} 元
        </el-col>
      </el-row>

      <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">
          订单状态
        </el-col>
        <el-col :span="12">
          {{ order.order.orderStatus }}
        </el-col>

      </el-row>

      <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">
          订单编号
        </el-col>
        <el-col :span="12">
          {{ order.order.id }}
        </el-col>
      </el-row>

      <el-row style="height:10%;width:100%;font-size:18px;margin:3% 0">
        <el-col :span="12">
          添加购物车时间
        </el-col>
        <el-col :span="12">
          <el-date-picker size="small" v-model="order.order.orderSignTime" format="YYYY年MM月DD日HH时mm分ss秒"
                          placeholder="购买日期"
                          disabled>
          </el-date-picker>
        </el-col>
      </el-row>

      <el-row style="width: 100%;"  justify="space-evenly">
        <el-col :span="4" v-if="store.state.isUser">
          <el-button class="el-button" plain round color="#626aef"
                     @click="buying(order.order.id,order.order.buyNumber,order.order.bookId)"
                     :disabled="(order.order.orderStatus === `已购买`)
                     || (order.order.orderStatus === `已签收`)
                     ||(order.order.orderStatus === `已退货`)
                     ||(order.order.buyNumber > order.order.book.bookNumber)"
          v-if="order.order.orderStatus === `未支付`">
            购买
          </el-button>
        </el-col>

        <el-col :span="4" v-if="store.state.isUser">
          <el-button class="el-button" plain round color="#626aef"
                     @click="confirmOrderById(order.order.id,order.order.bookNumber,order.order.bookId)"
                     :disabled="(order.order.orderStatus === `已签收`)
                     ||(order.order.orderStatus === `未支付`)
                     ||(order.order.orderStatus === `已退货`)"
                     v-if="order.order.orderStatus === `已购买`">
            确认收货
          </el-button>
        </el-col>

        <el-col :span="4" v-if="store.state.isUser">
          <el-button class="el-button" plain round color="#626aef"
                     @click="backOrderById(order.order.id, order.order.bookNumber,order.order.bookId)"
                     :disabled="(order.order.orderStatus === `已退货`) ||(order.order.orderStatus === `未支付`)"
                     v-if="order.order.orderStatus === `已签收`||(order.order.orderStatus === `已购买`)"
          >
            退货
          </el-button>
        </el-col>

        <el-col :span="4" v-if="store.state.isShopkeeper||store.state.isAdmin">
          <el-button class="el-button" plain round type="danger" @click="alter(order)">修改</el-button>
        </el-col>

        <el-col :span="4">
          <el-button class="el-button" plain round type="primary" @click="back">返回</el-button>
        </el-col>
      </el-row>

    </div>

    <div style="width:35%;margin: 6% 0 0 2%;float:right">
      <el-image :src="order.order.book.image.picture" style="width:70%"></el-image>
    </div>
  </div>
</template>

<script setup>
import {reactive, toRef} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";
import {alterOrder, backOrder, buy, confirmOrder, getOrder} from "@/api/OrderApi";

let router = useRouter()
let store = useStore()
// eslint-disable-next-line no-undef
const prop = defineProps(['orderId'])
let orderId = toRef(prop, 'orderId')
let order = reactive({
  order: {
    id: '',
    bookId: '',
    userId: '',
    wordId: '',
    commentStatus: '',
    bookPrice: 2,
    discount: 9.99,
    expressFare: 2,
    kickback: 0.35,
    orderSignTime: '',
    buyTime: '',
    backTime: '',
    confirmTime: '',
    orderStatus: '',
    buyNumber: 1,
    book: {
      id: '',
      seriesId: '',
      shopkeeperId: '',
      imageId: '',
      wordId: '',
      bookName: '',
      bookAuthor: '',
      bookFactory: '',
      bookAddDate: '',
      bookNumber: 10,
      bookPrice: 2,
      discount: 9.99,
      expressFare: 2,
      kickback: 0.35,
      series: {
        id: '',
        seriesName: ''
      },
      image: {
        id: "",
        picture: "",
      },
      word: {
        id: '',
        bookDescribe: '',
        bookDetail: '',
        comments: ''
      }
    },
    shopkeeper: {
      id: '',
      imageId: '',
      shopkeeperName: '1',
      shopkeeperPassword: '',
      shopkeeperQuality: '',
      shopkeeperBirth: '',
      shopkeeperSignTime: '',
      shopkeeperAddress: '',
      shopkeeperPhone: '',
      shopkeeperMoney: 0
    },
    user: {
      id: '',
      imageId: '',
      userName: "",
      userPassword: "",
      userSex: "",
      userAddress: "",
      userPhone: "",
      userMoney: 0,
    },
  }
})

getOrder(orderId.value).then(Response => {
  order.order = Response.data
  console.log(order.order)
})

let buying = ((v, num, bookId) => {
  console.log("这里是购物车的购买" + v)
  if (confirm("确认购买?")) {
    let ord = {
      id: v,
      buyNumber: num,
      bookId: bookId,
      userId: order.order.userId,
      bookPrice: order.order.bookPrice,
      expressFare: order.order.expressFare,
      discount: order.order.discount,
      user: {
        id: order.order.user.id,
        userAddress: order.order.user.userAddress,
        userPhone: order.order.user.userPhone
      }
    }
    let orderBuy = JSON.stringify(ord)
    buy(orderBuy).then(Response => {
      ElNotification.success({
        message: Response.messgae,
        position: 'top-left',
        title: '购买成功'
      })
      router.push({
        name: 'userOrder'
      })
    }).catch(error => {
      ElMessage.error(error.data.message)
    })
  }
})

let confirmOrderById=((v, num, bookId)=>{
  let con={
    id:v,
    buyNumber: num,
    bookId: bookId,
  }
  let confirmId=JSON.stringify(con)
  confirmOrder(confirmId).then(()=>{
    ElNotification.success({
      message: Response.messgae,
      position: 'top-left',
      title: '收货成功'
    })
    router.push({
      name: 'userOrder'
    })
  }).catch(error => {
    ElMessage.error(error.data.message)
  })
})

let backOrderById=((v, num, bookId)=>{
  let con={
    id:v,
    buyNumber: num,
    bookId: bookId,
  }
  let confirmId=JSON.stringify(con)
  backOrder(confirmId).then(()=>{
    ElNotification.success({
      message: Response.messgae,
      position: 'top-left',
      title: '退货成功'
    })
    router.push({
      name: 'userOrder'
    })
  }).catch(error => {
    ElMessage.error(error.data.message)
  })
})

let back = (() => {
  if (store.state.isShopkeeper) {
    router.push({
      name: 'shopkeeperOrder'
    })
  } else if (store.state.isUser) {
    router.push({
      name: 'userOrder'
    })
  }else {
    router.push({
      name: 'adminOrder'
    })
  }
})

let alter = ((order) => {
  let alOrder = {
    id: order.order.id,
    bookPrice: order.order.bookPrice,
    expressFare: order.order.expressFare,
    discount: order.order.discount,
    buyNumber: order.order.buyNumber,
    userId:order.order.userId,
    //管理员可以修改kickback，商家不可以修改
    kickback:order.order.kickback,
    user: {
      id: order.order.user.id,
      userAddress: order.order.user.userAddress,
      userPhone: order.order.user.userPhone
    }
  }
  let ao = JSON.stringify(alOrder)
  console.log(ao)
  alterOrder(ao).then(Response => {
    if (Response.code === 200) {
      ElNotification.success({
        message: Response.messgae,
        position: 'top-left',
        title: '修改成功'
      })
      back()
    }
  }).catch(error => {
    console.log(error)
    ElMessage.error(error.data.message)
  })
})

</script>
<style scoped>
.el-button {
  width: 100%;
}
</style>
