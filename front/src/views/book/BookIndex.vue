<template>
	<el-table stripe :data="books" highlight-current-row="true" height="100%" style="width: 100%;margin-top: 1%"
		label-width="20%">
		<!--    <el-table-column prop="bookId" label="书籍编号" width="120px"></el-table-column>-->
		<el-table-column fixed prop="bookPicture" label="封面">
			<template v-slot="scope">
				<el-image :src="scope.row.bookPicture" style="width: 100%;height: 100%;">
				</el-image>
			</template>
		</el-table-column>
		<el-table-column prop="bookInfo" label="简介"></el-table-column>
		<el-table-column prop="bookName" class-name="bookName" label="书名"></el-table-column>
		<el-table-column prop="bookAuthor" label="作者"></el-table-column>
		<el-table-column prop="seriesName" label="系列"></el-table-column>
		<el-table-column prop="bookPrice" label="价格"></el-table-column>
		<el-table-column prop="bookAddDate" label="发布日期"></el-table-column>
		<el-table-column prop="bookFactory" label="出版社"></el-table-column>
		<el-table-column prop="booknum" label="库存">
			<template v-slot="scope">
				<el-tag type="success">{{ scope.row.bookNum }}</el-tag>
			</template>
		</el-table-column>
		<el-table-column prop="bookId" label="操作">
			<template v-slot="scope">
				<el-button class="el-button" round type="success" @click="inf(scope.row.bookId)">详情</el-button>
				<el-button class="el-button" round @click="buy(scope.row.bookId)"
					v-show="scope.row.bookNum > 0">购买</el-button>
			</template>
		</el-table-column>
	</el-table>
</template>
<script setup>
import {
	reactive,
	ref
} from "vue";

import {
	useRouter
} from "vue-router";

import axios from 'axios'

import {
	useStore
} from "vuex";

let books = ref([])
let router = useRouter()
let store = useStore()

axios.get('api/book').then(Response => {
	books.value = Response.data
})

let bag = reactive({
	bookId: '',
	userId: '',
	bookNumber: 1
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

let buy = ((v) => {
	bag.bookId = v;
	console.log(v + "----" + bag.bookId)
	bag.userId = store.state.userId;
	console.log(bag.userId)
	if (store.state.userId == "" || store.state.userId == undefined || store.state.userId == null) {
		router.push({
			name: 'login'
		})
	} else {
		let addBag = JSON.stringify(bag)
		axios.post(`api/bag`, addBag, {
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(Response => {
			let message = Response.data;
			if (confirm("是否前往购物车?")) {
				router.push({
					name: 'userBag'
				})
			}
		}).catch(Error => {
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