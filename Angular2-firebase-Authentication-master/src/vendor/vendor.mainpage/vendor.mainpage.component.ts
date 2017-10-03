import {Component} from "@angular/core";
import {LocationStrategy} from "@angular/common";
import {AngularFire} from "angularfire2";
import {UserDetailsService} from "../../userdetail.service";
import {Router} from "@angular/router";
import {CreateVendorService} from "../vendor.service";
import {LoginService} from "../../login/login.service";


@Component({
  selector: 'admin-page',
  templateUrl: './vendor.mainpage.component.html',
  styleUrls: ['./vendor.mainpage.component.css']
})
export class VendorMainpageComponent {
  user = null;
  provider = null;
  logged_in;

  constructor(private af: AngularFire, private userDetailsService: UserDetailsService, private router: Router, private session: CreateVendorService,
              private url: LocationStrategy,private loginService:LoginService) {
    this.user = userDetailsService.getUserDetails();
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
      console.log('Session Clear Called');

      this.router.navigate(['/main']);
    });

  }
}

// user=null;
// constructor(private af: AngularFire, private userDetailsService: UserDetailsService, private router: Router,
//             private url: LocationStrategy) {
//   this.user = userDetailsService.getUserDetails();
// }


