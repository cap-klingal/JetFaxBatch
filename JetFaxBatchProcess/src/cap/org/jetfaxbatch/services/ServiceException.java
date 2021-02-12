/*
 * $Workfile:   ServiceException.java  $
 * $Revision:   1.0  $
 * $Author:   jxue  $
 * $Date:   Oct 05 2012 16:46:32  $
 */
package cap.org.jetfaxbatch.services;

import org.cap.core.CAPException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Checked Exception that is used to denote application level exception 
 * condition.
 * 
 * @author Mike Galvin
 * @version $Revision:   1.0  $
 */
public class ServiceException extends CAPException {

    /**
	 * Must be instantiated even if not used
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new <code>ServiceException</code> with no
     * message and no nested exception.
     */
    public ServiceException() {
    }

    /**
     * Creates a new <code>ServiceException</code> with the
     * specified message and no nested exception.
     *
     * @param msg the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }

    /**
     * Creates a new <code>ServiceException</code> with
     * nested exception.
     *
     * @param t the nested exception.
     */
    public ServiceException(Throwable t) {
        _exception = t;
    }

    /**
     * Creates a new <code>ServiceException</code> with
     * the specified message and nested exception.
     *
     * @param msg the detail message.
     * @param t the nested exception.
     */
    public ServiceException(String msg, Throwable t) {
        super(msg);
        _exception = t;
    }

    /**
     * Returns the nested exception.
     *
     * @return the nested exception, or null if there is no
     *   nested exception.
     */
    public Throwable getNestedException() {
        return _exception;
    }

    /**
     * Returns the exception message.  If the nested exception 
     * is not null then its message string is included in the 
     * message string.
     *
     * @return the exception message.
     */
    synchronized
    public String getMessage() {
        return (_exception == null) ?
            super.getMessage() :
            super.getMessage() + "; nested exception is:\n\t" + _exception;
    }

    /**
     * Writes the exception stack trace to the specified print
     * stream.  If the nested exception is non-null then its
     * stack trace is appended to the output stream.
     *
     * @param ps the output stream.  This argument cannot be null.
     */
    synchronized
    public void printStackTrace(PrintStream ps) {
        if (_exception == null) {
            super.printStackTrace(ps);
        }
        else {
            synchronized(ps) {
                ps.println(this);
                _exception.printStackTrace(ps);
            }
        }
    }

    /**
     * Writes the exception stack trace to the standard error
     * stream.
     * Equivalent to
     * <pre>
     *   printStackTrace(System.err)
     * </pre>
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * Writes the exception stack trace to the specified print
     * writer.  If the nested exception is non-null then its
     * stack trace is appended to the output stream.
     *
     * @param pw the output print writer.  This argument cannot be null.
     */
    synchronized
    public void printStackTrace(PrintWriter pw) {
        if (_exception == null) {
            super.printStackTrace(pw);
        }
        else {
            synchronized(pw) {
                pw.println(this);
                _exception.printStackTrace(pw);
            }
        }
    }

    //-----------------------------
    // implementation:
    //-----------------------------

    // nested exception.
    private Throwable _exception;
}