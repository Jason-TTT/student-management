const usernameInput = document.getElementById('usernameInput')
const passwordInput = document.getElementById('passwordInput')
const loginBtn = document.getElementById('loginBtn')
const message = document.getElementById('message')

loginBtn.addEventListener('click', login);
window.addEventListener("pageshow", clearLayout);
async function login() {
    const username=usernameInput.value;
    const password=passwordInput.value;

    const  response= await fetch("/auth/login",{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({
            userName:username,
            passWord:password
        })
    });

        const  result= await response.json();
        if(!response.ok){
            if(result.data){
                message.innerText=Object.values(result.data).join(";");
            }else{
                message.innerText=result.message;
            }
            return;
        }
        message.innerText="";
       // 跳转页面
       window.location.href="/index.html";
}
function clearLayout() {
    usernameInput.value = "";
    passwordInput.value = "";
    message.innerText="";
}

