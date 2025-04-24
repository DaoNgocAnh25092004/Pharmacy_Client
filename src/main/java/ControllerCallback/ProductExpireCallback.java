package ControllerCallback;

public interface ProductExpireCallback {
    void onProductExpireWithinAMonth(boolean isAddToInvoice);
    void onProductStillGood();
}
