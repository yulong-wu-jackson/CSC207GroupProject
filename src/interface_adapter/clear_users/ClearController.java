package interface_adapter.clear_users;

import use_case.clear_users.ClearInputBoundary;

import java.util.ArrayList;

public class ClearController {
    final private ClearInputBoundary userClearUseCaseInteractor;
    public ClearController(ClearInputBoundary userClearUseCaseInteractor) {
        this.userClearUseCaseInteractor = userClearUseCaseInteractor;
    }

    public void execute(){
        userClearUseCaseInteractor.execute();
    }
}
