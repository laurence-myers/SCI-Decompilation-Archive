;;; Sierra Script 1.0 - (do not remove this comment)
(script# 64899)
(include game.sh)
(use Main)
(use PolyPath)
(use Feature)

(public
	foEExit 0
	foWExit 1
	foNExit 2
	foSExit 3
)

(class ExitFeature of Feature
	(method (doVerb &tmp [temp0 2])
		(ego
			setMotion: PolyPath
				(- mouseX (plane left:))
				(- mouseY (plane top?))
				self
		)
	)
)

(class CUExitFeature of Feature
	(properties
		nsLeft 632
		nsTop 288
		nsRight 632
		nsBottom 288
		rExitTo 0
	)
	
	(method (init &tmp [temp0 4])
		(super init: &rest)
		(self forceCursor: (ScriptID 64006 10))
	)
	
	(method (doVerb)
		(curRoom newRoom: rExitTo)
	)
	
	(method (onMe theObjOrX theY &tmp temp0 temp1)
		(if (== argc 1)
			(= temp0 (theObjOrX x?))
			(= temp1 (theObjOrX y?))
		else
			(= temp0 theObjOrX)
			(= temp1 theY)
		)
		(return
			(not
				(if
					(and
						(<= 30 temp0)
						(<= temp0 (- (plane getWidth:) 30))
						(<= 30 temp1)
					)
					(<= temp1 (- (plane getHeight:) 30))
				else
					0
				)
			)
		)
	)
)

(instance foNExit of ExitFeature
	(method (init)
		(super init: &rest)
		(self forceCursor: (ScriptID 64006 1))
		(= nsTop 0)
		(= nsBottom 30)
		(= nsRight (plane right:))
		(= nsLeft 0)
	)
	
	(method (doVerb)
		(ego
			setMotion: PolyPath (- mouseX (plane left:)) -20 self
		)
	)
	
	(method (cue)
		(curRoom newRoom: (curRoom north?))
	)
)

(instance foSExit of ExitFeature
	(method (init)
		(super init: &rest)
		(self forceCursor: (ScriptID 64006 2))
		(= nsTop (- (plane bottom?) 30))
		(= nsBottom (plane bottom?))
		(= nsRight (plane right:))
		(= nsLeft 0)
	)
	
	(method (doVerb)
		(ego
			setMotion: PolyPath (- mouseX (plane left:)) (+ nsBottom 120) self
		)
	)
	
	(method (cue)
		(curRoom newRoom: (curRoom south?))
	)
)

(instance foEExit of ExitFeature
	(method (init)
		(super init: &rest)
		(self forceCursor: (ScriptID 64006 3))
		(= nsTop 0)
		(= nsBottom (plane bottom?))
		(= nsRight (plane right:))
		(= nsLeft (- (plane right:) 30))
	)
	
	(method (doVerb)
		(ego
			setMotion: PolyPath (+ nsRight 50) (- mouseY (plane top?)) self
		)
	)
	
	(method (cue)
		(curRoom newRoom: (curRoom east?))
	)
)

(instance foWExit of ExitFeature	
	(method (init)
		(super init: &rest)
		(self forceCursor: (ScriptID 64006 4))
		(= nsTop 0)
		(= nsBottom (plane bottom?))
		(= nsRight 30)
		(= nsLeft 0)
	)
	
	(method (doVerb)
		(ego
			setMotion: PolyPath -50 (- mouseY (plane top?)) self
		)
	)
	
	(method (cue)
		(curRoom newRoom: (curRoom west?))
	)
)
