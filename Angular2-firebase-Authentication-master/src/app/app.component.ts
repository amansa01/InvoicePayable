/**
* @Author: Tushar Agarwal(tusharcoder) <tushar>
* @Date:   2017-01-27T23:45:07+05:30
* @Email:  tamyworld@gmail.com
* @Filename: app.component.ts
* @Last modified by:   tushar
* @Last modified time: 2017-01-28T00:38:10+05:30
*/



import { Component } from '@angular/core';
import { AngularFire, AuthProviders  } from 'angularfire2';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {}
// title = 'app works';
// user=null;
// logged_in=false;
// provider=null;
// constructor(private af: AngularFire) {
//     this.af.auth.subscribe(user=>{
//       if(user){
//         this.user=user;
//       }else{
//         this.user={}
//       }
//     })
//   }
//
//   googlelogin(){
//     this.af.auth.login({
//       provider:AuthProviders.Google,
//     }).then((data:any)=>{
//       this.user=data;
//       console.log(this.user);
//       this.provider="Google";
//       this.logged_in=true;
//     });
//   }
//   twitterlogin(){
//     this.af.auth.login({
//       provider: AuthProviders.Twitter,
//     }).then((data:any)=>{
//       this.user=data;
//       console.log(this.user);
//       this.provider="Twitter";
//       this.logged_in=true;
//     });
//   }
//   facebooklogin(){
//     this.af.auth.login({
//       provider:AuthProviders.Facebook,
//     }).then((user)=>{
//       this.user=user;
//       console.log(this.user);
//       this.provider="Facebook";
//       this.logged_in=true;
//     });
//   }
//
//   githublogin(){
//     this.af.auth.login({
//       provider:AuthProviders.Github,
//     }).then((user:any)=>{
//       this.user=user;
//       console.log(this.user);
//       this.provider="Github";
//       this.logged_in=true;
//     });
//   }
//
//   logout(){
//     this.af.auth.logout().then(()=>{
//       this.logged_in=false;
//       this.user={};
//       this.provider=null;
//       alert("you are successfully logged out");
//     });
//
//   }
// }
