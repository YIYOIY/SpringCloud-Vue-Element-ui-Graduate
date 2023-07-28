<template>
  <el-table stripe :data="order" :highlight-current-row=true height="600" style="width: 100%;margin-top: 3%">
    <el-table-column prop="orderId" label="订单编号" align="center"></el-table-column>
    <el-table-column prop="book.bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.book.bookPicture" @click="inf(scope.row.bookId)"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="book.bookAddDate" label="上架日期"></el-table-column>
    <el-table-column prop="orderTime" label="加入购物车日期" width="180px">
      <template v-slot="scope">
        <el-tooltip placement="top" effect="customized">
          <template #content>
           {{scope.row.orderTime}}
          </template>
          <el-button type="info" style="border-radius: 10px">
            {{scope.row.orderTime.substring(0,10)}}
          </el-button>
        </el-tooltip>
      </template>
    </el-table-column>
    <el-table-column prop="bookNum" label="购买数量">
      <template v-slot="scope">
        <span>{{ scope.row.bookNum }} 本</span>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookNum" label="库存数量">
      <template v-slot="scope">
        <span>仅剩 {{ scope.row.book.bookNum }} 本</span>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookPrice" label="单价">
      <template v-slot="scope">
        <span>{{ scope.row.book.bookPrice }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="total" label="总价" sortable>
      <template v-slot="scope">
        <span>{{ scope.row.book.bookPrice * scope.row.bookNum }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="orderStatus" label="状态"></el-table-column>
    <el-table-column prop="[orderId,orderStutus,book.bookNum]" label="操作">
      <template v-slot="scope">
        <el-button class="el-button" round color="#626aef"
          @click="buy(scope.row.orderId, scope.row.bookNum, scope.row.bookId)"
          :disabled="(scope.row.orderStatus == `已购买`) || (scope.row.bookNum > scope.row.book.bookNum)">购买</el-button>
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
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ElMessage, ElNotification } from "element-plus";
import {deleteOrder, getUserOrder, getUserPage} from "@/api/OrderApi";
let order = ref([])
let pageNo = ref(1);
let pageTotal = ref(1);
let router = useRouter();
let store = useStore();

let inf = ((v) => {
  console.log(v)
  let id = v
  router.push({
    name: 'information',
    query: {
      id: id,
      order: true
    }
  })
})
getUserPage(pageNo.value,store.state.userId).then(Response=> {
  pageTotal.value = Response.data
  console.log(pageTotal.value)
})

getUserOrder(pageNo.value,store.state.userId).then(Response => {
  order.value = Response.data
  console.log(order.value)
})

let pageNoo = (() => {
  getUserOrder(pageNo.value,store.state.userId).then(Response => {
    order.value = Response.data
  })
})

let buy = ((orderId) => {
  router.push({
    name: 'orderConfirm',
    query: {
      orderId: orderId
    }
  })
})


let del = ((v) => {
  if (confirm("确认删除?")) {
    deleteOrder(v).then(Response => {
      ElNotification.success(Response.message)
      getUserOrder(pageNo.value,store.state.userId).then(Response => {
        order.value = Response.data
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

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #b2e68d, #bce689);
  right: 0;
}
</style>
