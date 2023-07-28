<template>
  <div style="width: 100%;height: 100%;margin-bottom: 3%">
    <el-carousel style="width: 100%;height: 100%" :interval=2000 :initial-index=0 height="550px" direction="horizontal"
                 type="card" :autoplay="true" arrow="hover" pause-on-hover loop indicator-position="outside"
                 trigger="hover">
      <el-carousel-item v-for="item in books" :key="item.bookId" style="margin: 2% 10%">
        <el-image :src=item.bookPicture style="width: 50%" @click="inf(item.bookId)">
        </el-image>
      </el-carousel-item>
    </el-carousel>
  </div>


  <el-row :gutter="10">
    <el-col title="按系列查询" :span="8">
      <el-form-item label="系列">
        <el-select v-model="seriesName" :model-value="seriesName" clearable placeholder="请选择系列"
                   @change="selectBySeries">
          <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                     :value="item.seriesName"></el-option>
        </el-select>
        <el-icon class="is-loading" size="large" style="margin: 0 5px">
          <Football/>
        </el-icon>
      </el-form-item>
    </el-col>
    <el-col title="按书名查询" :span="5">
      <el-form-item label="书籍">
        <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
      </el-form-item>
    </el-col>
    <el-col :span="4">
      <el-button type="primary" @click="selectByName">查询
        <el-icon class="is-loading" size="large" style="margin: 0px 5px">
          <Aim/>
        </el-icon>
      </el-button>
    </el-col>
  </el-row>

  <el-table stripe :data="books" :highlight-current-row=true height="800" style="width: 100%;margin-top: 1%"
            label-width="20%">
    <!--    <el-table-column prop="bookId" label="书籍编号" width="120px"></el-table-column>-->
    <el-table-column fixed prop="bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.bookPicture" style="width: 100%;height: 100%;" @click="inf(scope.row.bookId)">
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="bookInfo" label="简介"></el-table-column>
    <el-table-column prop="bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="bookAuthor" label="作者"></el-table-column>
    <el-table-column prop="seriesName" label="系列"></el-table-column>
    <el-table-column prop="bookPrice" label="价格" sortable></el-table-column>
    <el-table-column prop="bookAddDate" label="发布日期" sortable></el-table-column>
    <el-table-column prop="bookFactory" label="出版社"></el-table-column>
    <el-table-column prop="bookNum" label="库存" sortable></el-table-column>
    <el-table-column prop="bookId" label="操作">
      <template v-slot="scope">
        <el-button class="el-button" round type="success" @click="inf(scope.row.bookId)">书籍详情</el-button>
        <el-button class="el-button" round @click="buy(scope.row.bookId)"
                   v-show="scope.row.bookNum > 0">加入购物车
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {
  reactive,
  ref,
  onBeforeMount
} from "vue";

import {
  useRouter
} from "vue-router";

import {
  useStore
} from "vuex";
import {ElMessage} from "element-plus";
import {Aim, Football} from "@element-plus/icons-vue";
import {getBooks, getBooksByName, getSelectBySeries, getSeries} from "@/api/BookApi";
import {addOrder} from "@/api/OrderApi";


let books = ref([])
let router = useRouter()
let store = useStore()


let searchName = ref('')
let selectByName = (() => {
  getBooksByName(searchName.value).then(Response => {
    books.value = Response.data
    searchName.value=''
  })
})

let series = ref('')
onBeforeMount(async () => {
  await getSeries().then(Response => {
    series.value = Response.data
  })
})

let seriesName = ref('')
let selectBySeries = (() => {
  getSelectBySeries(seriesName.value).then(Response => {
    books.value = Response.data
  })
})

getBooks().then(Response => {
  books.value = Response.data
})

let inf = ((v) => {
  console.log(v)
  let id = v
  router.push({
    name: 'information',
    query: {
      id: id
    }
  })
})

let bag = reactive({
  bookId: '',
  userId: '',
  bookNum: 1
})

let buy = ((v) => {
  bag.bookId = v;
  console.log(v + "----左右两边都是bookId,左边是传过来的,右边是要发送后端的" + bag.bookId);
  bag.userId = store.state.userId;
  console.log(bag.userId);
  // 判断用户登陆了没，没登陆返回登录页面
  if (store.state.userId == "" || store.state.userId == undefined || store.state.userId == null) {
    router.push({
      name: 'login'
    })
  } else {
    let addBag = JSON.stringify(bag)
    addOrder(addBag).then(Response => {
      console.log(Response.message)
      ElMessage({
        showClose: true,
        message: Response.message,
        type: 'success'
      })
      if (confirm(Response.message + "是否前往购物车?")) {
        router.push({
          name: "userOrder",
        });
      }
    }).catch(Error => {
      ElMessage.error(Error.data.message)
      console.log(Error)
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
  margin-top: 2%;
  width: 80%;
}
</style>