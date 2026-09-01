const tbody = document.getElementById("studentBody");
const addBtn=document.getElementById("addBtn");
const searchBtn=document.getElementById("searchBtn");
const pageInFo=document.getElementById("pageInFo");
const prevBtn=document.getElementById("prevBtn");
const nextBtn=document.getElementById("nextBtn");
const pageSizeSelect=document.getElementById("pageSizeSelect");
const sortBySelect=document.getElementById("sortBySelect");
const orderBySelect=document.getElementById("orderBySelect");
const message=document.getElementById("message");
const currentUserName=document.getElementById("username");
const currentUserRole=document.getElementById("role");
const layoutBtn=document.getElementById("layoutBtn");
// 监听
addBtn.addEventListener("click",addStudent);
searchBtn.addEventListener("click",searchStudent);
nextBtn.addEventListener("click",pageNextStudents);
prevBtn.addEventListener("click",pagePrevStudents);
pageSizeSelect.addEventListener("change",changePageSize);
sortBySelect.addEventListener("change",changeSelectBy);
orderBySelect.addEventListener("change",changeSelectBy);
layoutBtn.addEventListener("click",layout);
//做判断是add还是update

let editingId=null;
// 查找需要
let pageSize=5;
let currentName="";
let currenClassId="";
let currentPage =1;
let totalPage =1;
let currentSortBy="id";
let currentOrderBy="asc";


async function loadStudents() {

    //让输入安全放入url    encodeURLComponent
    const  url = `/student?name=${encodeURIComponent(currentName)}&classId=${encodeURIComponent(currenClassId)}&page=${currentPage}&size=${pageSize}&sortBy=${currentSortBy}&order=${currentOrderBy}`;
    const response = await fetch(url);
    const result = await response.json();

    if(!response.ok){
        // 用data的用于是把error放进了data
        if(result.data){
            //把数据拿出来
            const errors=Object.values(result.data);
            // 每条数据分开
            message.innerText=errors.join(";");
        }else {
            message.innerText = result.message;
        }
        return;
    }
    message.innerText="";

    const students = result.data.records;
    totalPage=result.data.totalPage;
    pageInFo.innerHTML=`第${currentPage} / ${totalPage} 页`;
    // 要想执行函数得先找到 studentBody 这个字名
    // 创建表格行
    // 每次的刷新⭐
    tbody.innerHTML = "";

    for (let i = 0; i < students.length; i++) {
        const student = students[i];
        // tr一定要循环一次 创建一次
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td>${student.id}</td>
        <td>${student.studentName}</td>
        <td>${student.studentAge}</td>
        <td>${student.classId}</td>
        `;


        const actionTd = document.createElement("td");

        const deleteBtn = document.createElement("button");
        const updateBtn = document.createElement("button");

        deleteBtn.innerText = "删除";
        updateBtn.innerText = "修改";

        actionTd.appendChild(deleteBtn);
        actionTd.appendChild(updateBtn);

        tr.appendChild(actionTd);
        tbody.appendChild(tr);


        //deleteBtn.addEventListener("click", function () {
        //         deleteStudent(student.id);
        //     }); 带了（）代表立马执行  此次为匿名函数 等于需要自己套id 才能执行
        deleteBtn.addEventListener("click", function () {
            deleteStudent(student.id);
        });
        // 实际是走到add函数里的
        updateBtn.addEventListener("click", function () {
            editStudent(student);
        });
        //   塞到tbody里

    }
}
    // 检查登入
async function checkLogin() {
    const response = await fetch("/auth/me");
    const result = await response.json();
        if(!response.ok){
            window.location.href="login.html";
            return false;
        }
        currentUserName.innerText=result.data.sysUserName;
        currentUserRole.innerText=result.data.sysUserRole;
        return true;
}
async function initPage() {
    const flag=await checkLogin();
    if(!flag){
        return;

    }else{
        loadStudents();
    }
}
initPage();

    async function addStudent() {
        // value  指的是拿这里的东西
        const studentName = document.getElementById("nameInput").value;
        const studentAge = Number(document.getElementById("ageInput").value);
        const classId = Number(document.getElementById("classIdInput").value);

        const student = {
            studentName: studentName,
            studentAge: studentAge,
            classId: classId
        };

        let response;
        if (editingId === null) {
             response = await fetch("/student", {
                method: "POST",
                // 就是把 JS 对象转换成 JSON 字符串
                headers: {
                    "content-Type": "application/json"
                },
                body: JSON.stringify(student)
            });
        } else {
             response = await fetch(`/student/${editingId}`, {
                method: "PUT",
                headers: {
                    "content-Type": "application/json"
                },
                body: JSON.stringify(student)
            });
        }
        const result = await response.json();
        if(!response.ok){
            if(result.data){
                const errors=Object.values(result.data);
                message.innerText=errors.join(";");
            }else {
                message.innerText = result.message;
            }
            return;
        }
        message.innerText="";

        editingId = null;
        addBtn.innerText="添加学生"
        //清空
        document.getElementById("nameInput").value = "";
        document.getElementById("ageInput").value = "";
        document.getElementById("classIdInput").value = "";
        message.innerText="";



        // await 防止刷新提前弹出来
        await loadStudents();
    }

    async function deleteStudent(id) {
        const response = await fetch(`/student/${id}`, {
            method: "DELETE",
        });
        const result = await response.json();
        if(!response.ok){
            if(result.data){
                const errors=Object.values(result.data);
                message.innerText=errors.join(";");
            }else {
                message.innerText = result.message;
            }
            return;
        }
        message.innerText="";
        await loadStudents();
    }

     function editStudent(student) {

        editingId = student.id;

        document.getElementById("nameInput").value = student.studentName;
        document.getElementById("ageInput").value = student.studentAge;
        document.getElementById("classIdInput").value = student.classId;

        addBtn.innerText="保存修改";
    }

    async function searchStudent(){
        currentName = document.getElementById("searchNameInput").value.trim();
        currenClassId=document.getElementById("searchClassIdInput").value.trim();
        currentPage=1;
        //currentName 传入loadStudent
        await loadStudents();
    }

    async function pageNextStudents() {
        if (currentPage < totalPage) {
            currentPage++;
            pageSize=Number(pageSizeSelect.value);
            await loadStudents();
        }
    }

    async function pagePrevStudents() {
            if (currentPage > 1) {
                currentPage--;
                pageSize=Number(pageSizeSelect.value);
                await loadStudents();
            }
        }
    async function changePageSize() {
        pageSize=Number(pageSizeSelect.value);
        currentPage=1;
        await loadStudents();
    }
    async function  changeSelectBy() {
        currentSortBy=sortBySelect.value;
        currentOrderBy=orderBySelect.value;
        currentPage=1;
        await loadStudents();
    }

    async function layout(){
        const response = await fetch("/auth/layout", {
            method: "POST",
        });
        const result = await response.json();

        if(!response.ok){
         message.innerText=result.message;
         return;
        }
        window.location.href="/login.html";
    }