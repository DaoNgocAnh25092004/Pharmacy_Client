package com.example.pharmacyproject.ControllerCallback;

public interface ProductExpireCallback {
    void onProductExpireWithinAMonth(boolean isAddToInvoice);
    void onProductStillGood();
}
