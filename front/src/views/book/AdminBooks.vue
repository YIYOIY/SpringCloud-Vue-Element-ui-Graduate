<template>
  <el-button @click="add()" type="info" style="margin-top: 1%">添加新书籍</el-button>
  <el-button type="warning"><a href="excel/exportExcel">excel表格导出所有书籍</a></el-button>
  <el-table stripe :data="books" :highlight-current-row=true height="100%" style="width: 96%; margin-left:2%"
    label-width="30%">
    <!--    <el-table-column prop="bookId" label="书籍编号" width="120px"></el-table-column>-->
    <el-table-column fixed prop="bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.bookPicture" style="width: 100%; height: 100%">
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="bookInfo" label="简介" style="font-size: small"></el-table-column>
    <el-table-column prop="bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="bookAuthor" label="作者"></el-table-column>
    <el-table-column prop="seriesName" label="系列"></el-table-column>
    <el-table-column prop="bookPrice" label="价格" sortable></el-table-column>
    <el-table-column prop="bookAddDate" label="发布日期" sortable></el-table-column>
    <el-table-column prop="bookFactory" label="出版社"></el-table-column>
    <el-table-column prop="bookNum" label="库存" sortable></el-table-column>
    <el-table-column prop="bookId" label="操作">
      <template v-slot="scope">
        <el-button class="el-button" round color="#626aef" @click="alter(scope.row.bookId)">修改</el-button>
        <el-button class="el-button" round type="danger" @click="del(scope.row.bookId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import {deleteBook, getBooks} from "@/api/BookApi";
import {ElMessage} from "element-plus";
let books = ref([]);
let router = useRouter();

getBooks().then((Response) => {
  books.value = Response.data;
});

let alter = (v) => {
  console.log("这里是管理书籍的准备进入修改书籍" + v);
  let id = v;
  router.push({
    name: "alterBooks",
    query: {
      bookId: id,
    },
  });
};

let del = (v) => {
  if (confirm("确认删除?")) {
    deleteBook(v).then((Response) => {
        ElMessage.success(Response.message);
      getBooks().then((Response) => {
          books.value = Response.data;
        });
      }).catch((Error) => {
        ElMessage.error(Error.data.message)
        console(Error + "请稍后重试!");
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

.el-button {
  margin-left: 10%;
  width: 80%;
  margin-top: 2%;
}
</style>
