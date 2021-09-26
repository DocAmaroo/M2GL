/**
 */
package maps;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Public Space</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link maps.PublicSpace#getName <em>Name</em>}</li>
 *   <li>{@link maps.PublicSpace#getBorderedBy <em>Bordered By</em>}</li>
 * </ul>
 *
 * @see maps.MapsPackage#getPublicSpace()
 * @model abstract="true"
 * @generated
 */
public interface PublicSpace extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see maps.MapsPackage#getPublicSpace_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link maps.PublicSpace#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Bordered By</b></em>' reference list.
	 * The list contents are of type {@link maps.Road}.
	 * It is bidirectional and its opposite is '{@link maps.Road#getBorder <em>Border</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bordered By</em>' reference list.
	 * @see maps.MapsPackage#getPublicSpace_BorderedBy()
	 * @see maps.Road#getBorder
	 * @model opposite="border" required="true"
	 * @generated
	 */
	EList<Road> getBorderedBy();

} // PublicSpace
