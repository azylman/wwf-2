package com.zylman.wwf.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.zylman.wwf.shared.InputValidator;
import com.zylman.wwf.shared.SolveResult;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WwfSolverView extends Composite {
	@UiTemplate("WwfSolver.ui.xml")
	interface Binder extends UiBinder<Widget, WwfSolverView> { }
	private static final Binder binder = GWT.create(Binder.class); 
	
	@UiField TextBox rack;
	@UiField TextBox start;
	@UiField TextBox contains;
	@UiField TextBox end;
	@UiField Button sendButton;
	@UiField Label errorLabel;
	@UiField CellTable<SolveResult> results;
	@UiField TextBox test;
	@UiField Label testResults;
	
	private final WwfSolverPresenter presenter;

	public final ListDataProvider<SolveResult> dataProvider = new ListDataProvider<SolveResult>();
	public final List<SolveResult> resultList = dataProvider.getList();

	public WwfSolverView() {
		initWidget(binder.createAndBindUi(this));
		presenter = new WwfSolverPresenter(this);
		
		// Focus the cursor on the name field when the app loads
		rack.setFocus(true);
		sendButton.setWidth("100px");

		initializeColumns();
	}
	
	private void initializeColumns() {
		TextColumn<SolveResult> wordColumn = new TextColumn<SolveResult>() {
			@Override public String getValue(SolveResult object) {
				return object.getWord();
			}
		};
		TextColumn<SolveResult> scoreColumn = new TextColumn<SolveResult>() {
			@Override public String getValue(SolveResult object) {
				return object.getScore().toString();
			}
		};
		TextColumn<SolveResult> lengthColumn = new TextColumn<SolveResult>() {
			@Override public String getValue(SolveResult object) {
				return object.getLength().toString();
			}
		};
		wordColumn.setSortable(true);
		scoreColumn.setSortable(true);
		lengthColumn.setSortable(true);
		results.addColumn(wordColumn, "Word");
		results.addColumn(lengthColumn, "Length");
		results.addColumn(scoreColumn, "Score");
		// Add a ColumnSortEvent.ListHandler to connect sorting to the
		// java.util.List.
		ListHandler<SolveResult> wordColumnSortHandler = new ListHandler<SolveResult>(resultList);
		wordColumnSortHandler.setComparator(wordColumn, new Comparator<SolveResult>() {
			public int compare(SolveResult o1, SolveResult o2) {
				if (o1 == o2) {
					return 0;
				}
				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getWord().compareTo(o2.getWord()) : 1;
				}
				return -1;
			}
		});
		ListHandler<SolveResult> lengthColumnSortHandler = new ListHandler<SolveResult>(resultList);
		lengthColumnSortHandler.setComparator(lengthColumn, new Comparator<SolveResult>() {
			public int compare(SolveResult o1, SolveResult o2) {
				if (o1 == o2) {
					return 0;
				}
				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getLength().compareTo(o2.getLength()) : 1;
				}
				return -1;
			}
		});
		ListHandler<SolveResult> scoreColumnSortHandler = new ListHandler<SolveResult>(resultList);
		scoreColumnSortHandler.setComparator(scoreColumn, new Comparator<SolveResult>() {
			public int compare(SolveResult o1, SolveResult o2) {
				if (o1 == o2) {
					return 0;
				}
				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getScore().compareTo(o2.getScore()) : 1;
				}
				return -1;
			}
		});

		results.addColumnSortHandler(wordColumnSortHandler);
		results.addColumnSortHandler(lengthColumnSortHandler);
		results.addColumnSortHandler(scoreColumnSortHandler);
		results.getColumnSortList().push(scoreColumn);
		results.setPageSize(999999);
		
		dataProvider.addDataDisplay(results);
	}
	
	@UiHandler("sendButton")
	void handleClick(ClickEvent e) {
		if (rack.getText().length() > 1) {
			presenter.setSolveHistory(rack.getText(), start.getText(), contains.getText(), end.getText());
		}
	}

	@UiHandler("rack")
	void handleRackKeyUp(KeyUpEvent event) {
		if (validateInput()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendButton.click();
		}
	}
	
	@UiHandler("start")
	void handleStartKeyUp(KeyUpEvent event) {
		if (validateInput()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendButton.click();
		}
	}
	
	@UiHandler("contains")
	void handleContainsKeyUp(KeyUpEvent event) {
		if (validateInput()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendButton.click();
		}
	}
	
	@UiHandler("end")
	void handleEndKeyUp(KeyUpEvent event) {
		if (validateInput()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendButton.click();
		}
	}

	@UiHandler("test")
	void handleTestKeyUp(KeyUpEvent event) {
		if (test.getText().isEmpty()) {
			testResults.setText("");
		} else {
			errorLabel.setText("");
			testResults.setText("");
		}
		History.newItem("test/" + test.getText());
	}
	
	public boolean validateInput() {
		if (!InputValidator.validateRack(rack.getText())) {
			errorLabel.setText("Invalid query. The rack may only contain letters and up to two wildcards,"
					+ " to a maximum of 10 characters.");
			return false;
		} else if (
					!InputValidator.validateOther(start.getText())
					|| !InputValidator.validateOther(contains.getText())
					|| !InputValidator.validateOther(end.getText())) {
			errorLabel.setText("Invalid query. The requirement fields may only contain letters.");
			return false;
		}
		errorLabel.setText("");
		return true;
	}
	
	public void setError(String error) {
		errorLabel.setText(error);
	}
	
	public void setResults(List<SolveResult> results) {
		resultList.clear();
		resultList.addAll(results);
	}
}
