const userNameInput=document.getElementById("userNameInput");
const userPasswordInput=document.getElementById("userPasswordInput");
const errorMsg=document.getElementById("error");
const registerBtn=document.getElementById("registerBtn");

registerBtn.addEventListener("click",register);

async function register(){
    const username=userNameInput.value;
    const password=userPasswordInput.value;

    const response=await fetch("/auth/register",{
        method:"POST",
        headers:{
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            userName: username,
            passWord: password,
        })
    });
    const result=await response.json();
    if(!response.ok){
        if(result.data){
            errorMsg.innerText=Object.values(result.data).join(";");
        }else {
            errorMsg.innerText=result.message;
        }
        return;
    }
    errorMsg.innerText="";
    window.location.href="login.html";
}