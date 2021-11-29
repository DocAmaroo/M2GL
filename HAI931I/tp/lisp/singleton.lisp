(defclass singleton-class (standard-class)
  ((uniqueInstance :accessor getUniqueInstance :initform nil))
  (:metaclass standard-class))

(defmethod make-instance ((newC singleton-class) &rest initargs)
  (print 'called)
  (let ((x (getUniqueInstance newC)))
    (if (eq x nil)
	(let ((newI (call-next-method)))
	  (print 'enCoursDeCreation)
	  (setf (getUniqueInstance newC) newI)
	  newI)
      (progn (print 'existeDeja)
	x))))

(defclass test (standard-object)
  ((x :initvalue 33)) ;;pas d'attributs
  (:metaclass singleton-class))

;;(print (getUniqueInstance 'test))

(make-instance 'test)

(eq (make-instance 'test) (make-instance 'test))
