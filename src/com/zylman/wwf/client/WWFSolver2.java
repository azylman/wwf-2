package com.zylman.wwf.client;

import java.util.Comparator;
import java.util.List;

import com.zylman.wwf.shared.Result;
import com.zylman.wwf.shared.SolveResult;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WWFSolver2 implements EntryPoint
{
	
  private final WwfSolveServiceAsync wwfSolveService = GWT.create(WwfSolveService.class);
  private final WwfWordTestServiceAsync wwfWordTestService = GWT.create(WwfWordTestService.class);
	
  final Button sendButton = new Button("Submit");
	final TextBox rack = new TextBox();
	final TextBox start = new TextBox();
	final TextBox contains = new TextBox();
	final TextBox end = new TextBox();
	final TextBox test = new TextBox();
	final CellTable<SolveResult> results = new CellTable<SolveResult>();
	final Label errorLabel = new Label();
	final ListDataProvider<SolveResult> dataProvider = new ListDataProvider<SolveResult>();
	final List<SolveResult> resultList = dataProvider.getList();
	final Label testResults = new Label();
  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		TextColumn<SolveResult> wordColumn = new TextColumn<SolveResult>() {
			@Override
			public String getValue(SolveResult object) {
				return object.getWord();
			}
		};
		
		TextColumn<SolveResult> scoreColumn = new TextColumn<SolveResult>() {
			@Override
			public String getValue(SolveResult object) {
				return object.getScore().toString();
			}
		};
		
		TextColumn<SolveResult> lengthColumn = new TextColumn<SolveResult>() {
			@Override
			public String getValue(SolveResult object) {
				return object.getLength().toString();
			}
		};
		
		wordColumn.setSortable(true);
		scoreColumn.setSortable(true);
		lengthColumn.setSortable(true);
		results.addColumn(wordColumn, "Word");
		results.addColumn(lengthColumn, "Length");
		results.addColumn(scoreColumn, "Score");
		
		dataProvider.addDataDisplay(results);
  	
  	// Add a ColumnSortEvent.ListHandler to connect sorting to the
    // java.util.List.
    ListHandler<SolveResult> wordColumnSortHandler = new ListHandler<SolveResult>(
        resultList);
    wordColumnSortHandler.setComparator(wordColumn,
        new Comparator<SolveResult>() {
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
    
    ListHandler<SolveResult> lengthColumnSortHandler = new ListHandler<SolveResult>(
        resultList);
    lengthColumnSortHandler.setComparator(lengthColumn,
        new Comparator<SolveResult>() {
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

    ListHandler<SolveResult> scoreColumnSortHandler = new ListHandler<SolveResult>(
        resultList);
    scoreColumnSortHandler.setComparator(scoreColumn,
        new Comparator<SolveResult>() {
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
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("rackContainer").add(rack);
		RootPanel.get("startContainer").add(start);
		RootPanel.get("containsContainer").add(contains);
		RootPanel.get("endContainer").add(end);
		RootPanel.get("testContainer").add(test);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("resultsContainer").add(results);
		RootPanel.get("testResultsContainer").add(testResults);

		// Focus the cursor on the name field when the app loads
		rack.setFocus(true);
		rack.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class SolveHandler implements ClickHandler, KeyUpHandler
		{
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event)
			{
				getAnagrams();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
				{
					getAnagrams();
				}
			}
		}
		
		class TestHandler implements KeyUpHandler
		{
			public void onKeyUp(KeyUpEvent event)
			{
				testWord();
			}
		}
		
		// Add a handler to send the name to the server
		SolveHandler solveHandler = new SolveHandler();
		TestHandler testHandler = new TestHandler();
		sendButton.addClickHandler(solveHandler);
		rack.addKeyUpHandler(solveHandler);
		test.addKeyUpHandler(testHandler);
	}
	
	private void getAnagrams() {
    // Set up the callback object.
    AsyncCallback<Result> callback = new AsyncCallback<Result>() {
      public void onFailure(Throwable caught) {
      	errorLabel.setText("failure!");
      }

      public void onSuccess(Result results) {
      	resultList.clear();
        resultList.addAll(results.getWords());
      }
    };

    // Make the call to the solve service.
    wwfSolveService.findAnagrams(
    		rack.getText(),
    		start.getText(),
    		contains.getText(),
    		end.getText(),
    		callback);
	}
	
	private void testWord() {
		AsyncCallback<Result> callback = new AsyncCallback<Result>() {
			public void onFailure(Throwable caught) {
      	errorLabel.setText("failure!");
      }

      public void onSuccess(Result results) {
      	if(results.getError()) {
      		testResults.setText("That's not a word.");
      	} else {
      		testResults.setText(results.getQuery() + " is worth " + results.getWords().get(0).getScore() + " points!");
      	}
      }
    };
    
    errorLabel.setText("");
    testResults.setText("");
    wwfWordTestService.testWord(test.getText(), callback);
	}
}
