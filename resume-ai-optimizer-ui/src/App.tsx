import { useState } from "react";
import { Upload, FileText, CheckCircle, AlertCircle, Sparkles } from "lucide-react";

// Shadcn UI Components
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";

export default function App() {
  // State variables for our form
  const [file, setFile] = useState<File | null>(null);
  const [jobDescription, setJobDescription] = useState("");
  
  // State to simulate loading and results
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [showMockResult, setShowMockResult] = useState(false);

  // Function to simulate sending data to the Java Backend
  const handleAnalyzeClick = () => {
    setIsAnalyzing(true);
    // Simulate a 2-second API call
    setTimeout(() => {
      setIsAnalyzing(false);
      setShowMockResult(true);
    }, 2000);
  };

  return (
    <div className="min-h-screen bg-zinc-50 py-10 px-4 sm:px-6 lg:px-8 font-sans text-zinc-900">
      
      {/* Header Section */}
      <div className="max-w-5xl mx-auto mb-10 text-center">
        <h1 className="text-4xl font-extrabold tracking-tight text-zinc-900 flex items-center justify-center gap-3">
          <Sparkles className="w-10 h-10 text-blue-600" />
          Resume AI Optimizer
        </h1>
        <p className="mt-4 text-lg text-zinc-500 max-w-2xl mx-auto">
          Upload your PDF resume, paste the job description, and let our AI analyze your match percentage and missing keywords.
        </p>
      </div>

      {/* Main Dashboard Grid */}
      <div className="max-w-5xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-8">
        
        {/* LEFT COLUMN: Input Form */}
        <Card className="shadow-sm border-zinc-200">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Upload className="w-5 h-5 text-blue-600" />
              Upload Data
            </CardTitle>
            <CardDescription>Provide your resume and the target job description.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            
            {/* File Upload */}
            <div className="space-y-2">
              <label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                1. Upload Resume (PDF)
              </label>
              <Input 
                type="file" 
                accept=".pdf" 
                onChange={(e) => setFile(e.target.files?.[0] || null)}
                className="cursor-pointer file:text-blue-600 file:font-semibold"
              />
            </div>

            {/* Job Description Textarea */}
            <div className="space-y-2">
              <label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                2. Job Description
              </label>
              <Textarea 
                placeholder="Paste the requirements and description of the job you are applying for..." 
                className="min-h-[200px] resize-none border-zinc-300 focus-visible:ring-blue-500"
                value={jobDescription}
                onChange={(e) => setJobDescription(e.target.value)}
              />
            </div>

            {/* Submit Button */}
            <Button 
              className="w-full bg-blue-600 hover:bg-blue-700 text-white transition-colors" 
              size="lg"
              onClick={handleAnalyzeClick}
              disabled={isAnalyzing || !jobDescription} // Disable if empty or loading
            >
              {isAnalyzing ? "Analyzing with AI..." : "Analyze Resume"}
            </Button>
          </CardContent>
        </Card>

        {/* RIGHT COLUMN: Results Display */}
        <div className="space-y-6">
          {!showMockResult && !isAnalyzing && (
             <Card className="h-full flex flex-col items-center justify-center text-center p-10 bg-zinc-100/50 border-dashed shadow-none">
               <FileText className="w-16 h-16 text-zinc-300 mb-4" />
               <p className="text-zinc-500 font-medium">Your AI analysis results will appear here.</p>
             </Card>
          )}

          {isAnalyzing && (
            <Card className="h-full flex flex-col items-center justify-center p-10 shadow-sm border-blue-100 bg-blue-50/50">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mb-4"></div>
              <p className="text-blue-600 font-medium animate-pulse">Running advanced AI analysis...</p>
            </Card>
          )}

          {/* MOCK RESULTS CARD (Simulating the Java API response) */}
          {showMockResult && (
            <Card className="shadow-sm border-zinc-200 animate-in fade-in slide-in-from-bottom-4 duration-500">
              <CardHeader className="pb-4 border-b border-zinc-100">
                <CardTitle className="text-xl">Analysis Results</CardTitle>
                <CardDescription>Target Company: <span className="font-semibold text-zinc-900">Tech Corp (Mocked)</span></CardDescription>
              </CardHeader>
              <CardContent className="pt-6 space-y-8">
                
                {/* Match Score */}
                <div className="space-y-3">
                  <div className="flex justify-between items-end">
                    <span className="font-semibold text-zinc-700 flex items-center gap-2">
                      <CheckCircle className="w-5 h-5 text-emerald-500" />
                      Match Percentage
                    </span>
                    <span className="text-3xl font-bold text-emerald-600">75%</span>
                  </div>
                  <Progress value={75} className="h-3 bg-zinc-100" />
                </div>

                {/* Missing Keywords */}
                <div className="space-y-3">
                  <span className="font-semibold text-zinc-700 flex items-center gap-2">
                    <AlertCircle className="w-5 h-5 text-rose-500" />
                    Missing Keywords
                  </span>
                  <div className="flex flex-wrap gap-2">
                    {/* Mocked missing keywords */}
                    <Badge variant="destructive" className="bg-rose-100 text-rose-700 hover:bg-rose-200 border-none shadow-none">AWS Certified Cloud Practitioner</Badge>
                    <Badge variant="destructive" className="bg-rose-100 text-rose-700 hover:bg-rose-200 border-none shadow-none">Docker</Badge>
                    <Badge variant="destructive" className="bg-rose-100 text-rose-700 hover:bg-rose-200 border-none shadow-none">JUnit/Mockito</Badge>
                  </div>
                </div>

                {/* Improvement Tips */}
                <div className="space-y-2 p-4 bg-blue-50 rounded-lg border border-blue-100">
                  <span className="font-semibold text-blue-900 block mb-1">AI Improvement Tips</span>
                  <p className="text-sm text-blue-800 leading-relaxed">
                    Highlight your expertise in testing by explicitly mentioning JUnit and Mockito in your recent Java projects. Also, emphasize any Cloud Computing studies or certifications to align better with the AWS requirements of this role.
                  </p>
                </div>

              </CardContent>
            </Card>
          )}
        </div>
      </div>
    </div>
  );
}
