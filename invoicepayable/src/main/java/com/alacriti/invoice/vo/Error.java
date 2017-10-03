package com.alacriti.invoice.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

private String code = null;
private String message = null;
private String field = null;

/**
**/

@JsonProperty("code")
public String getCode() {
return code;
}

public Error(String code, String message, String field) {
super();
this.code = code;
this.message = message;
this.field = field;
}

public void setCode(String code) {
this.code = code;
}

/**
**/

@JsonProperty("message")
public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

/**
* Name of the invalid parameter.
**/

@JsonProperty("field")
public String getField() {
return field;
}

public void setField(String field) {
this.field = field;
}

@Override
public boolean equals(Object o) {
if (this == o) {
return true;
}
if (o == null || getClass() != o.getClass()) {
return false;
}
Error error = (Error) o;
return Objects.equals(code, error.code) && Objects.equals(message, error.message)
&& Objects.equals(field, error.field);
}

@Override
public int hashCode() {
return Objects.hash(code, message, field);
}

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("class Error {\n");

sb.append("    code: ").append(toIndentedString(code)).append("\n");
sb.append("    message: ").append(toIndentedString(message)).append("\n");
sb.append("    field: ").append(toIndentedString(field)).append("\n");
sb.append("}");
return sb.toString();
}

/**
* Convert the given object to string with each line indented by 4 spaces
* (except the first line).
*/
private String toIndentedString(Object o) {
if (o == null) {
return "null";
}
return o.toString().replace("\n", "\n    ");
}
}