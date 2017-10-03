import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Headers} from '@angular/http';
import {ConstantComponent} from "../constant.component";

@Injectable()
export class LoginService {
  loginData: any;
  session: any;
  constructor(private http: Http,private url:ConstantComponent) {  }
  loginValues(loginData: any) {
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
   this.loginData = this.http.post(this.url.login , loginData, {headers,withCredentials: true}).toPromise();
   return this.loginData;
  }
  verifyToken(loginData: any) {
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    this.loginData = this.http.post(this.url.verifyToken , loginData, {headers,withCredentials: true}).toPromise();
    return this.loginData;
  }
  sessionClear() {
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    this.session = this.http.get(this.url.clearSession, { withCredentials: true}).toPromise();
    return this.session;
  }

}
