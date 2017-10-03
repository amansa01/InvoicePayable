import {Component} from '@angular/core';
import {AngularFire, AuthMethods, AuthProviders} from 'angularfire2';
import {Router} from '@angular/router';
import {LoginService} from "./login.service";
import {UserDetailsService} from "../userdetail.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.html'
})
export class LoginComponent {
  title = 'app works';
  user = null;
  logged_in = false;
  provider = null;
  email: string;
  test: any;

  constructor(private af: AngularFire, private router: Router, private loginService: LoginService, private detailService: UserDetailsService) {

  }

  twitterlogin() {

    this.af.auth.login({
      provider: AuthProviders.Twitter,
      method: AuthMethods.Popup
    }).then((data: any) => {
      this.user = data;
      console.log('In Twitter Method', data);
      this.provider = 'Twitter';
      this.logged_in = true;
      // firebase.auth().currentUser.uid().then(function (idToken) {
      //   this.http.post(this.url.verifyToken , idToken, {withCredentials: true});
      //   return this.loginData;
      // }).catch(function (error) {
      //   return console.log(error);
      // });
      this.email = this.user.auth.providerData[0].email;
      this.test = {'email': this.email};
      this.loginService.loginValues(this.test).then((response: any) => {
        console.log(response.json());
        if (this.user.auth.providerData[0].email === 'amansaini011@gmail.com') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/vendor']);
        }
      }).catch((error: any) => {
        return console.log(error);
      });

    });

  }

  // logout(){
  //   this.af.auth.logout().then(()=>{
  //     this.logged_in=false;
  //     this.user={};
  //     this.provider=null;
  //     alert("you are successfully logged out");
  //   });
  //
  // }
}
