package com.example.pharmacyproject.ControllerCallback;

import java.util.Map;

public interface FeverPrescriptionCallback {
    void thuocForAdults(Map<Object, Integer> thuocList);
    void thuocForChildren(Map<Object, Integer> thuocList);
}
