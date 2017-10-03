import {Component} from "@angular/core";
import {AngularFire} from "angularfire2";
import {UserDetailsService} from "../userdetail.service";
import {Router} from "@angular/router";
import {LocationStrategy} from "@angular/common";
import {LoginService} from "../login/login.service";

@Component({
  selector: 'admin-page',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  user ;
  provider = null;
  logged_in ;

  constructor(private af: AngularFire, private loginService:LoginService,private userDetailsService: UserDetailsService, private router: Router,
              private url: LocationStrategy) {
    this.af.auth.subscribe(user => {
      if (user) {
        console.log('in User Details', user)
        this.user = user;
      }
    });
    //this.user = userDetailsService.getUserDetails();
    console.log('user in constructor: ', this.user);
  }

  logout() {
    this.af.auth.logout().then(() => {
      this.logged_in = false;
      this.user = {};
      this.provider = null;
      this.loginService.sessionClear().then((response: any) => {
        console.log(response.json());
      }).catch((error: any) => {
        return console.log(error);
      });
      alert("you are successfully logged out");
      this.router.navigate(['/main']);
    });

  }
}
