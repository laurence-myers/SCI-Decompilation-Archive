;;; Sierra Script 1.0 - (do not remove this comment)
(script# 64036)
(include sci.sh)
(use Main)
(use GenDialog)
(use String)
(use File)

(public
	proc64036_0 0
)

(procedure (proc64036_0 &tmp newStr newFile)
	(if version (proc64896_7 version) (= version 0))
	(= newStr (Str new:))
	(newStr format: {%s%s} curSaveDir {version})
	(if (= newFile (File new:))
		(newFile name: (newStr data?))
		(if (newFile open: 1)
			(if (= version (Str newWith: 200 {}))
				(newFile readString: version 199)
			)
			(newFile close:)
		)
		(newStr dispose:)
		(newFile dispose:)
	)
	(if (or (not version) (not (version size:)))
		(= version (Str with: {devel}))
	)
	(DisposeScript -1500)
)
