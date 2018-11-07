package assignment;
import javax.swing.table.AbstractTableModel;

public class animeModel extends AbstractTableModel {

	private String[] columns;
		private Object[][] rows;
		
		/**
		 * Constructor with parameter row and column
		 */
		public animeModel(Object [][] data, String[] columnName) {
			
			this.rows = data;
			this.columns = columnName;
		}
		
		/**
	     * @method to set the first column should return the object in image icon
	     */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int column) {
				return getValueAt(0,column).getClass();
		}
			
		@Override
		/**
	     * @return the number of rows
	     */
		public int getRowCount() {
			return this.rows.length;
		}

		@Override
		/**
	     * @return the number of column
	     */
		public int getColumnCount() {
			return this.columns.length;
		}

		@Override
		/**
	     * @return the object of specific row and column
	     */
		public Object getValueAt(int rowIndex, int columnIndex) {
			return this.rows[rowIndex][columnIndex];
		}
		
		/**
	     * @return the column name
	     */
		public String getColumnName(int col) {
			return this.columns[col];
		}

	}
