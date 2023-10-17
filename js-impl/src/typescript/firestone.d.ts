export declare var firestone: Firestone

export interface Firestone {

	cwd: string

	/**
	 * Firestone customized Console instance.
	 */
	console: Console

	backup: boolean

	isFile(path: string): boolean

	isDir(path: string): boolean

	newPlain(path: string): void

	readText(path: string): string

	writeText(path: string, text: string): void

	appendText(path: string, text: string): void

	newPlainTextModifierScope(block: (scope: PlainTextModifierScope) => void): void

	log(message?: any): void

}

export interface Console {
	log(...message: (string | undefined)[])
	info(...message: (string | undefined)[])
	warn(...message: (string | undefined)[])
	error(...message: (string | undefined)[])
	waitEnter()
	prompt(message?: string): string
}

export interface PlainTextModifierScope {

	/**
	 * Match any line, and replace them with the result of replacer.
	 * @param replacer A function that takes a line and returns a new line.
	 */
	replace(replacer: (line: string) => string): void

	/**
	 * Matches the text in the line, and replace it with the result of replacer.
	 * @param match the keyword
	 * @param replacer A function that takes a line and returns a new line.
	 */
	ifContainsThenReplace(match: string, replacer: (line: string) => string): void

	/**
	 * Performs the replacement on the file.
	 * @param path the file path
	 */
	applyOn(path: string): void
}