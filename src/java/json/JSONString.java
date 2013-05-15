/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

public interface JSONString {
    /**
* The <code>toJSONString</code> method allows a class to produce its own JSON
* serialization.
*
* @return A strictly syntactically correct JSON text.
*/
    public String toJSONString();
}