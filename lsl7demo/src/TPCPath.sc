;;; Sierra Script 1.0 - (do not remove this comment)
(script# 64022)
(include sci.sh)
(use Array)
(use Motion)


(class TPCPath of Motion
	(properties
		scratch 0
		client 0
		caller 0
		x 0
		y 0
		dx 0
		dy 0
		b-moveCnt 0
		b-i1 0
		b-i2 0
		b-di 0
		b-xAxis 0
		b-incr 0
		completed 0
		xLast 0
		yLast 0
		backwards 0
		points 0
		value 0
	)
	
	(method (init theClient theCaller &tmp temp0 temp1)
		(if (not points)
			(= points (IntArray newWith: (- argc 1) -32768))
		)
		(if argc
			(= client theClient)
			(= temp1 (- (points indexOf: -32768) 1))
			(= temp0 0)
			(while (<= temp0 (- argc 3))
				(points at: (++ temp1) [theCaller temp0])
				(points at: (++ temp1) [theCaller (++ temp0)])
				(++ temp0)
			)
			(if (<= temp0 (- argc 2)) (= caller [theCaller temp0]))
		)
		(points at: (++ temp1) -32768)
		(self setTarget:)
		(super init:)
		(if (not argc) (self doit:))
	)
	
	(method (dispose)
		(if points (points dispose:))
		(super dispose:)
	)
	
	(method (moveDone)
		(cond 
			((== (points at: value) -32768) (= value 0))
			((< value 0) (= value (- (points size:) 1)))
			(else (self init:))
		)
	)
	
	(method (setTarget)
		(if
		(or (< 0 value) (!= (points at: value) -32768))
			(= x (points at: value))
			(if backwards (-- value) else (++ value))
			(= y (points at: value))
			(if backwards (-- value) else (++ value))
		)
	)
)
