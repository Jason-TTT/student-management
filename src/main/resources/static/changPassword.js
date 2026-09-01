const check=document.getElementById('check');
const oldPassword1=document.getElementById('oldPassword');
const newPassword1=document.getElementById('newPassword');
const message=document.getElementById('message');
check.addEventListener("click",updatePassword);

async function updatePassword(){
    const oldpassword=oldPassword1.value;
    const newpassword=newPassword1.value;

    const response = await fetch("auth/change-password",{
       method:"POST",
        headers: {
            "Content-Type": "application/json"
        },
       body:JSON.stringify({
           oldPassword:oldpassword,
           newPassword:newpassword
       })
    });
    const result = await response.json();

    if(!response.ok){
        if(result.data){
            message.innerText=Object.values(result.data).join(";");
        }
        message.innerText=result.message;
        return;
    }
    window.location.href="/login.html";
}