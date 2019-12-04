package a8;

public class Menu {
	
private GOLView view;
private GOLModel model;
private GOLController controller;
public Menu(GOLModel m, GOLView v, GOLController c) {
    view = v;
    model = m;
    controller = c;
}

//You can close the menu's principal any time by calling this...
public void closeAllInMenu() {
    model = null;
    view = null;
    controller = null;
}
}