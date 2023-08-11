<template>
  <div class="ac">
  <el-table stripe :data="order" :highlight-current-row=true height="600" style="width: 100%;margin-top: 3%">
    <el-table-column type="expand">
      <template #default="props">
        <div style="float: left;margin-top:4%;width:10%;left: 10%;position:relative;">
          <el-image :src="props.row.book.image.picture" style="width: 100%; height: 100%"/>
        </div>
        <div style="float: right;width:30%;margin: 2% 0 2% 0">
          <p>所属企业： {{ props.row.book.shopkeeper.shopkeeperName }}</p>
          <p>企业资质： {{ props.row.book.shopkeeper.shopkeeperQuality }}</p>
          <p>企业联系方式： {{ props.row.book.shopkeeper.shopkeeperPhone }}</p>
          <p>企业联系地址： {{ props.row.book.shopkeeper.shopkeeperAddress.substr(0, 25) }}</p>
          <p>企业注册日期： {{ props.row.book.shopkeeper.shopkeeperSignTime }}</p>
          <el-tooltip placement="bottom" effect="light">
            <template #content>
              <el-date-picker style="width: 150%;" v-model="props.row.orderSignTime" type="datetime" format="YYYY 年 MM 月 DD 日 HH 时 mm 分 ss 秒"/>
            </template>
            <el-button plain round type="info" >
              订单生成日期： {{props.row.orderSignTime.substring(0,10)}}
            </el-button>
          </el-tooltip>
        </div>
        <div style="float: right;width:30%;margin: 2% 2% 2% 5%">
          <p>状态： {{ props.row.orderStatus }}</p>
          <p>折扣%： {{ props.row.discount * 10 }}%</p>
          <p>运费： {{ props.row.expressFare }}</p>
          <p>单价： {{ props.row.bookPrice }}</p>
          <p>数量： {{ props.row.buyNumber }}</p>
          <p>总价： {{
              (props.row.bookPrice * props.row.buyNumber) * (props.row.discount ? props.row.discount : 10) / 10
              + props.row.expressFare
            }}</p>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="id" label="订单编号" align="center"></el-table-column>
    <el-table-column prop="book.bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.book.image.picture" @click="inf(scope.row.book.id)"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="book.bookAddDate" label="上架日期"></el-table-column>
    <el-table-column prop="orderSignTime" label="加入购物车日期" width="180px">
      <template v-slot="scope">
        <el-tooltip placement="top" effect="customized">
          <template #content>
           {{scope.row.orderSignTime}}
          </template>
          <el-button plain round type="info" >
            {{scope.row.orderSignTime.substring(0,10)}}
          </el-button>
        </el-tooltip>
      </template>
    </el-table-column>
    <el-table-column prop="buyNumber" label="购买数量">
      <template v-slot="scope">
        <span>{{ scope.row.buyNumber }} 本</span>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookNumber" label="库存数量">
      <template v-slot="scope">
        <span>仅剩 {{ scope.row.book.bookNumber }} 本</span>
      </template>
    </el-table-column>
    <el-table-column prop="book.bookPrice" label="单价">
      <template v-slot="scope">
        <span>{{ scope.row.book.bookPrice }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="total" label="总价" sortable>
      <template v-slot="scope">
        <span>{{ (scope.row.bookPrice * scope.row.buyNumber) * (scope.row.discount ? scope.row.discount : 10) / 10 + scope.row.expressFare }} 元</span>
      </template>
    </el-table-column>
    <el-table-column prop="orderStatus" label="状态"></el-table-column>
    <el-table-column prop="[id,orderStatus,book.bookNumber]" label="操作">
      <template v-slot="scope">
        <el-button class="el-button" plain round color="#626aef"
          @click="buying(scope.row.id, scope.row.bookNumber, scope.row.bookId)">详情</el-button>
        <el-button class="el-button" plain round type="danger" @click="del(scope.row.id,scope.row.wordId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";
import {deleteOrder, getUserOrder} from "@/api/OrderApi";

let order = ref([])
let pageNo = ref(1);
let pageSize = ref(10);
let router = useRouter();
let store = useStore();

let inf = ((v) => {
  console.log(v)
  router.push({
    name: 'information',
    query: {
      id: v,
      order: true
    }
  })
})

getUserOrder(pageNo.value,pageSize.value,store.state.userId).then(Response => {
  order.value = Response.data.data
  console.log(order.value)
})

let buying = ((orderId) => {
  router.push({
    name: 'orderConfirm',
    query: {
      orderId: orderId
    }
  })
})


let del = ((v,wId) => {
  if (confirm("确认删除?")) {
    let delOrder={
      id:v,
      bookId:wId
    }
    let ord=JSON.stringify(delOrder)
    deleteOrder(ord).then(Response => {
      ElNotification.success(Response.message)
      getUserOrder(pageNo.value,pageSize.value,store.state.userId).then(Response => {
        order.value = Response.data.data
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

/deep/ .el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #b2e68d, #bce689);
  right: 0;
}

.ac {
  width: 100%;
  height: 100%;
  position: fixed;
  margin: 3% 1%;
}
</style>
