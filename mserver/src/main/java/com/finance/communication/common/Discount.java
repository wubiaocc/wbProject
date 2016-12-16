package com.finance.communication.common;
/**
 * 积分折扣
 * @author test
 *
 */
public class Discount {
  String point;//累积点数
  String upto;//达到多少
  String discount;//所打折扣
public String getPoint() {
	return point;
}
public void setPoint(String point) {
	this.point = point;
}
public String getUpto() {
	return upto;
}
public void setUpto(String upto) {
	this.upto = upto;
}
public String getDiscount() {
	return discount;
}
public void setDiscount(String discount) {
	this.discount = discount;
}
@Override
public String toString() {
	return "Discount [point=" + point + ", upto=" + upto + ", discount="
			+ discount + "]";
}

}
