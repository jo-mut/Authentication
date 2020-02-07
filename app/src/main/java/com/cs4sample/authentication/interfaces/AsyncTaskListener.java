package com.cs4sample.authentication.interfaces;

import com.cs4sample.authentication.models.Proffession;

import java.util.List;

public interface AsyncTaskListener {
    void updateResult(List<Proffession> proffessions);
}
