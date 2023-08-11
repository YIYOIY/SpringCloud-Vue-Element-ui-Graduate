<template>
  <div class="background">
    <el-button @click="add()" plain round type="warning" style="top: 16%;left: 40%;position: absolute">添加新书籍
    </el-button>
    <el-button @click="search(searchName)" style="top: 16%;left: 25%;position: absolute" plain round type="info">
      查找书籍
    </el-button>
    <el-button @click="search('')" style="top: 16%;left: 32%;position: absolute" plain round type="primary">
      全部书籍
    </el-button>
    <el-form>
      <el-form-item label="书籍名称" prop="adminName" style="top: 16%;left: 5%;position: absolute">
        <el-input type="text" v-model="searchName" autofocus maxlength="20" clearable
                  style="max-width: 200px" placeholder="输入书名"></el-input>
      </el-form-item>
    </el-form>
    <el-table stripe :data="books" :highlight-current-row=true height="530" width="1000" style="margin-top: 13%"
              tooltip-effect="light">
      <el-table-column fixed type="expand">
        <template #default="props">
          <div style="float: left;margin-top:4%;width:10%;left: 10%;position:relative;">
            <el-image :src="props.row.image.picture" style="width: 100%; height: 100%"></el-image>
          </div>
          <div style="float: right;width:70%;margin: 2% 2% 2% 5%">
            <p><b>书籍编号:</b> {{ props.row.id }}</p>
            <p><b>出版社:</b> {{ props.row.bookFactory }}</p>
            <p><b>发布日期:</b> {{ props.row.bookAddDate }}</p>
            <p><b>上架日期:</b> {{ props.row.bookSignTime }}</p>
            <p><b>更新日期:</b> {{ props.row.version }}</p>
            <p><b>书籍简介:</b> {{ props.row.word.bookDescribe }}</p>
            <p><b>书籍详情:</b></p> {{ props.row.word.bookDetail }}
          </div>
        </template>
      </el-table-column>
      <el-table-column fixed type="index" label="序号" width="80"/>
      <el-table-column fixed prop="bookName" class-name="bookName" label="书名" width="160"></el-table-column>
      <el-table-column fixed prop="image.picture" label="封面" width="100">
        <template v-slot="scope">
          <el-image :src="scope.row.image.picture" style="width: 100%; height: 100%"></el-image>
        </template>
      </el-table-column>
      <el-table-column fixed prop="bookAuthor" label="作者" :show-overflow-tooltip="true" width="180"></el-table-column>
      <el-table-column fixed prop="series.seriesName" label="系列" width="150"></el-table-column>
      <el-table-column fixed prop="bookPrice" label="价格（元）" sortable width="120"/>
      <el-table-column fixed prop="discount" label="折扣（%）" sortable width="120"/>
      <el-table-column fixed prop="expressFare" label="运费（元）" sortable width="120"/>
      <el-table-column fixed prop="kickback" label="抽成（%）" sortable width="120"/>
      <el-table-column fixed prop="bookNumber" label="库存" sortable width="120"/>
      <el-table-column fixed prop="id" label="操作" width="200">
        <template v-slot="scope">
          <el-row :gutter="10">
            <el-col :span="12">
              <el-button class="el-button" plain round color="#626aef" @click="alter(scope.row.id)">修改</el-button>
            </el-col>
            <el-col :span="12">
              <el-button class="el-button" plain round type="danger" @click="del(scope.row.id)">删除</el-button>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {deleteBook, shopkeeperGetBooks} from "@/api/BookApi";
import {ElMessage, ElNotification} from "element-plus";

let store = useStore()
let books = ref([]);
let router = useRouter();

let searchName = ref('')
let pageNo = ref(1)
let pageSize = ref(15)
let search = ((v) => {
  if (v === '') v = null
  shopkeeperGetBooks(v, pageNo.value, pageSize.value,store.state.shopkeeperId).then(Response => {
    books.value = Response.data.data
  }).catch((error) => {
    if (error.data.code === 404) {
      ElNotification({
        title: '书籍未收录',
        message: error.data.message,
        type: "error",
        position: "top-left",
        offset: 100,
      })
      books.value = null
    }
  })
})


shopkeeperGetBooks("null", pageNo.value, pageSize.value,store.state.shopkeeperId).then((Response) => {
  books.value = Response.data.data;
});

let alter = (v) => {
  console.log("这里是管理书籍的准备进入修改书籍" + v);
  router.push({
    name: "alterBooks",
    query: {
      bookId: v,
    },
  });
};

let del = (v) => {
  if (confirm("确认删除?")) {
    let book = {
      id: v
    }
    let Bo = JSON.stringify(book)
    deleteBook(Bo).then((Response) => {
      ElMessage.success(Response.message);
      shopkeeperGetBooks("null", pageNo.value, pageSize.value,store.state.shopkeeperId).then((Response) => {
        books.value = Response.data.data;
      });
    }).catch((Error) => {
      ElMessage.error(Error.data.message)
      console.log(Error + "请稍后重试!");
    });
  }
};

const add = () => {
  router.push({
    name: "addBook",
  });
};
</script>
<style scoped>
/deep/ .bookName .cell {
  padding-left: 25%;
}

a {
  color: #69c4e0;
  text-decoration-line: none;
}

.background {
  margin: 0 3.5%;
}
</style>
