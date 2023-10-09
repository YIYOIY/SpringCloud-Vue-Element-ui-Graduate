<template>
  <div class="ac">
    <el-table stripe :data="order" :highlight-current-row=true height="530" width="1000" style="margin-top: 8%">
      <el-table-column type="expand">
        <template #default="props">
          <div style="float: left;margin-top:4%;width:10%;left: 10%;position:relative;">
            <el-image :src="props.row.book.image.picture?props.row.book.image.picture:`img/未设置图片时的404.jpg`" style="width: 100%; height: 100%"/>
          </div>
          <div style="float: right;width:30%;margin: 2% 0 2% 0">
            <p>消费者名称： {{ props.row.user.userName }}</p>
            <p>消费者联系方式： {{ props.row.user.userPhone }}</p>
            <p>消费者联系地址： {{ props.row.user.userAddress.substr(0, 25) }}</p>
            <p>所属企业： {{ props.row.book.shopkeeper.shopkeeperName }}</p>
            <p>企业资质： {{ props.row.book.shopkeeper.shopkeeperQuality }}</p>
            <p>企业联系方式： {{ props.row.book.shopkeeper.shopkeeperPhone }}</p>
            <p>企业联系地址： {{ props.row.book.shopkeeper.shopkeeperAddress.substr(0, 25) }}</p>
            <p>企业注册日期： {{ props.row.book.shopkeeper.shopkeeperSignTime }}</p>
            <el-tooltip placement="top" effect="light">
              <template #content>
                {{props.row.orderSignTime}}
              </template>
              <el-button plain round type="info" >
                订单生成日期： {{props.row.orderSignTime.substring(0,10)}}
              </el-button>
            </el-tooltip>
          </div>

          <div style="float: right;width:30%;margin: 2% 2% 2% 5%">
            <p>状态： {{ props.row.orderStatus }}</p>
            <p>平台回扣： {{ props.row.kickback * 10 }}%</p>
            <p>折扣立减： {{ 100-props.row.discount * 10 }}%</p>
            <p>运费： {{ props.row.expressFare }}</p>
            <p>单价： {{ props.row.bookPrice }}</p>
            <p>数量： {{ props.row.buyNumber }}</p>
            <p>总价： {{
                (props.row.bookPrice * props.row.buyNumber) * ((props.row.discount ? props.row.discount :10) / 10)
                + props.row.expressFare
              }}</p>
            <p>商家收益： {{
                ((props.row.bookPrice * props.row.buyNumber) * ((props.row.discount ? props.row.discount :10) / 10) + props.row.expressFare)
                -
                ((props.row.bookPrice * props.row.buyNumber) * ((props.row.discount ? props.row.discount : 0) / 10) + props.row.expressFare) * ((props.row.kickback?props.row.kickback:0)/10)
              }}</p>
            <p>管理员回扣收益： {{
                (
                    (props.row.bookPrice * props.row.buyNumber) * ((props.row.discount ? props.row.discount :10)/10)+props.row.expressFare)

                *((props.row.kickback?props.row.kickback:0) /10)}}</p>

          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="订单编号" align="center"></el-table-column>
      <el-table-column prop="book.bookPicture" label="封面">
        <template v-slot="scope">
          <el-image :src="scope.row.book.image.picture?scope.row.book.image.picture:`img/未设置图片时的404.jpg`"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="book.bookName" class-name="bookName" label="书名"></el-table-column>
      <el-table-column prop="user.userName" label="用户"></el-table-column>
      <el-table-column prop="book.shopkeeper.shopkeeperName" label="企业"></el-table-column>
      <el-table-column prop="orderSignTime" label="加入购物车日期" width="250">
        <template v-slot="scope">
          <el-date-picker size="small" v-model="scope.row.orderSignTime" type="datetime"
                          format="YYYY年MM月DD日HH时mm分ss秒"
                          placeholder="加入购物车日期">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="buyNumber" label="购买数量">
        <template v-slot="scope">
          <span>{{ scope.row.buyNumber }} 本</span>
        </template>
      </el-table-column>
      <el-table-column prop="book.bookNum" label="库存数量">
        <template v-slot="scope">
          <span>{{ scope.row.book.bookNumber }} 本</span>
        </template>
      </el-table-column>
      <el-table-column prop="book.bookPrice" label="单价">
        <template v-slot="scope">
          <span>{{ scope.row.bookPrice }} 元</span>
        </template>
      </el-table-column>
      <el-table-column prop="total" label="总价">
        <template v-slot="scope">
          <span>{{
              (scope.row.bookPrice * scope.row.buyNumber) * (scope.row.discount ? scope.row.discount : 10) / 10 + scope.row.expressFare
            }} 元</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderStatus" label="状态"></el-table-column>

      <el-table-column prop="[id,orderStatus,book.bookNumber]" label="操作">
        <template v-slot="scope">
          <el-button class="el-button" plain round type="warning" @click="alter(scope.row.id)">修改</el-button>
          <el-button class="el-button" plain round type="danger" @click="del(scope.row.id,scope.row.wordId,scope.row.userId,
          scope.row.bookPrice,scope.row.expressFare,scope.row.buyNumber,scope.row.discount,scope.row.orderStatus)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 15,20,30,50,100,200,400,1000]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage, ElNotification} from "element-plus";
import {deleteOrder, shopkeeperOrder} from "@/api/OrderApi";
let router=useRouter()
let store=useStore()
let order =ref([])
let pageNo = ref(1)
let pageSize = ref(5)
let total = ref(1)

shopkeeperOrder(pageNo.value,pageSize.value,store.state.shopkeeperId).then(Response => {
  order.value = Response.data.data
  pageSize.value = Response.data.pageSize
  total.value = parseInt(Response.data.total)
  pageNo.value = Response.data.current
  console.log(order.value)
})

let handleSizeChange = ((val) => {
  shopkeeperOrder(pageNo.value,val,store.state.shopkeeperId).then(Response => {
    order.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let handleCurrentChange = ((val) => {
  shopkeeperOrder(val, pageSize.value,store.state.shopkeeperId).then(Response => {
    order.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let alter = ((orderId) => {
  router.push({
    name: 'orderConfirm',
    query: {
      orderId: orderId
    }
  })
})

let del = ((id,wid,uid,bookprice,expressfare,buynum,discount,os) => {
  if (confirm("确认删除?")) {
    let delOrder = {
      id: id,
      wordId: wid,
      buyNumber: buynum,
      userId: uid,
      bookPrice: bookprice,
      expressFare: expressfare,
      discount: discount,
      orderStatus: os
    }
    let ord = JSON.stringify(delOrder)
    deleteOrder(ord).then(Response => {
      ElNotification.success(Response.message)
      shopkeeperOrder(pageNo.value, pageSize.value,store.state.shopkeeperId).then(Response => {
        order.value = Response.data.data
        pageSize.value = Response.data.pageSize
        total.value = parseInt(Response.data.total)
        pageNo.value = Response.data.current
        console.log(order.value)
      })
    }).catch(Error => {
      console.log(Error)
      ElMessage.error(Error.data.message + "请稍后重试!")
    })
  }
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

.ac {
  margin: 0 2%;
}
</style>
