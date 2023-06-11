<template>
  <el-table stripe :data="order" :highlight-current-row=true height="600" style="width: 100%;margin-top: 3%">
    <el-table-column prop="orderId" label="订单编号" align="center"></el-table-column>
    <el-table-column prop="book.bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.book.bookPicture"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="book.bookAddDate" label="上架日期"></el-table-column>
    <el-table-column prop="orderTime" label="购买日期">
      <template v-slot="scope">
        <el-date-picker size="small" v-model="scope.row.orderTime" type="date" format="YYYY/MM/DD-HH-mm-ss"
          placeholder="购买日期" disabled style="font-size:xx-small">
        </el-date-picker>
      </template>
    </el-table-column>
    <el-table-column prop="bookNum" label="购买数量">
      <template v-slot="scope">
        <span>{{ scope.row.bookNum }} 本</span>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookPrice" label="单价">
      <template v-slot="scope">
        <span>{{ scope.row.book.bookPrice }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="total" label="总价">
      <template v-slot="scope">
        <span>{{ scope.row.book.bookPrice * scope.row.bookNum }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="orderStatus" label="状态"></el-table-column>
    <el-table-column prop="[orderId,orderStutus]" label="操作">
      <template v-slot="scope">
        <el-button class="el-button" round color="#626aef"
          @click="buy(scope.row.orderId, scope.row.bookNum, scope.row.bookId)"
          :disabled="scope.row.orderStatus == `已购买`">购买</el-button>

        <el-button class="el-button" round type="danger" @click="del(scope.row.orderId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button-group>
    <el-button @click="pageNoo(pageNo++)" v-show="pageNo < pageTotal">下一页</el-button>
    <el-button @click="pageNoo(pageNo--)" v-show="pageNo > 1">上一页</el-button>
  </el-button-group>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios'
import { useStore } from "vuex";
let order = ref([])

let pageNo = ref(1);
let pageTotal = ref(1);
let router = useRouter()
let store = useStore();

axios.get(`api/userPageCount?userId=${store.state.userId}`).then(Response => {
  pageTotal.value = Response.data
})

axios.get(`api/userOrder?pageNo=${pageNo.value}&userId=${store.state.userId}`).then(Response => {
  order.value = Response.data
  console.log(order.value)
})

let pageNoo = (() => {
  axios.get(`api/userOrder?pageNo=${pageNo.value}&userId=${store.state.userId}`).then(Response => {
    order.value = Response.data
  })
})

let buy = ((v, num, bookId) => {
  console.log("这里是购物车的购买" + v)
  if (confirm("确认购买?")) {
    axios.put(`api/buyOrder?orderId=${v}&num=${num}&bookId=${bookId}`).then(Response => {
      let message = Response.data
      alert(message)
      axios.get(`api/userOrder?pageNo=${pageNo.value}&userId=${store.state.userId}`).then(Response => {
        order.value = Response.data
      })
    }).catch(Error => {
      alert(Error.message + "请稍后重试!")
    })
  }
})

let del = ((v) => {
  if (confirm("确认删除?")) {
    axios.delete(`api/order?orderId=${v}`).then(Response => {
      let message = Response.data
      alert(message)
      axios.get(`api/userOrder?pageNo=${pageNo.value}&userId=${store.state.userId}`).then(Response => {
        order.value = Response.data
      })
    }).catch(Error => {
      alert(Error.message + "请稍后重试!")
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
</style>
