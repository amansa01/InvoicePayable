import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Http, Response} from '@angular/http';
import {map} from 'rxjs/operator/map';
import {ConstantComponent} from "./constant.component";
import {FirebaseAuth, FirebaseAuthState} from "angularfire2";
import {Observable} from "rxjs/Observable";
// @Injectable()
// export class AuthGuard implements CanActivate{
//   isvalidUser= false;
//   result= false;
//   constructor(private router: Router , private _http: Http,private url: ConstantComponent) {}
//
//   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
//     this._http.get(this.url.session, {withCredentials: true })
//       .map((response: Response) => response.json())
//       .subscribe(
//         data => this.isvalidUser = data,
//         error => {},
//         () => {
//           if (this.isvalidUser === true) {
//             this.result = true;
//           }   else {
//             this.router.navigate(['']);
//             this.result = false;
//           }
//         }
//       );
//     return this.result;
//   }
// }
@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private auth: FirebaseAuth, private router: Router) {
  }

  canActivate(): Observable<boolean> {
    return this.auth
      .take(1)
      .map((authState: FirebaseAuthState) => !!authState)
      .do(authenticated => {
        if (!authenticated) {this.router.navigate(['']);}
      });
  }
}
